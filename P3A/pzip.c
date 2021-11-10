#include <stdio.h>

#include <sys/mman.h>

#include <stdio.h>

#include <fcntl.h>

#include <unistd.h>

#include <sys/stat.h>

#include <sys/sysinfo.h>

#include <stdlib.h>

#include <string.h>

#include <pthread.h>

typedef struct bound
{

    int start;

    int end;

} bound;

typedef struct argpack
{

    char *ptr;

    bound bd;

} argpack;

typedef struct run
{

    int reps;

    char ch;

} __attribute__((packed)) run;

typedef struct retpack
{

    run *runs;

    int n;

} retpack;

bound *get_even_bounds(int n, int size);

void *zip_t(void *args);

retpack *zip(char *str, bound bd);

void *printboundpack(void *args);

void printbound(char *arr, bound bd);

run *writezip(int nprocs, pthread_t *threads, run *holdout);

int main(int argc, char *argv[])
{

    if (argc < 2)
    {

        printf("pzip: file1 [file2 ...]\n");

        exit(1);
    }

    int nprocs = get_nprocs();

    int validf = 0;

    run *holdout = NULL;

    for (int f = 1; f < argc; f++)
    {

        char *fileName = argv[f];

        int fd;

        if ((fd = open(fileName, O_RDONLY)) < 0)
        {

            continue;
        }

        validf++;

        struct stat statbuf;

        if (fstat(fd, &statbuf) < 0)
        {

            printf("Couldn't get file stats\n");

            exit(1);
        }

        char *ptr = mmap(NULL, statbuf.st_size, PROT_READ, MAP_SHARED, fd, 0);

        if (ptr == MAP_FAILED)
        {

            printf("Mapping failed\n");

            exit(1);
        }

        close(fd);

        bound *bounds = get_even_bounds(nprocs, statbuf.st_size);

        pthread_t *threads = malloc(sizeof(pthread_t) * nprocs);

        argpack *args;

        for (int i = 0; i < nprocs; i++)
        {

            args = malloc(sizeof *args);

            args->ptr = ptr;

            args->bd = bounds[i];

            if (pthread_create(&threads[i], NULL, zip_t, args) != 0)
            {

                printf("Unable to create thread %d\n", i);

                exit(1);
            }
        }

        holdout = writezip(nprocs, threads, holdout);

        munmap(ptr, statbuf.st_size);

        free(bounds);

        free(threads);
    }

    if (validf)
    {

        fwrite(holdout, sizeof(run), 1, stdout);
    }

    return 0;
}

run *writezip(int nprocs, pthread_t *threads, run *holdout)
{

    retpack *ret1;

    retpack *ret2;

    pthread_join(threads[0], (void *)&ret2);

    int i = 1;

    while (ret2 == NULL)
    {

        pthread_join(threads[i++], (void *)&ret2);
    }

    if (holdout != NULL)
    {

        run *begin = &ret2->runs[0];

        if (holdout->ch == begin->ch)
        { // if there's an overlap, easy - just add extra occurrences to beginning run

            begin->reps += holdout->reps;
        }

        else
        { // if not, it's harder - we now have to add back to beginning of new array, shifting contents over

            int *n = &ret2->n;

            ret2->runs = realloc(ret2->runs, *n * (sizeof(run) + 1));

            memmove(ret2->runs + 1, ret2->runs, *n * sizeof(run));

            ret2->runs[0] = *holdout;

            *n += 1;
        }
    }

    for (; i < nprocs; i++)
    {

        ret1 = ret2;

        pthread_join(threads[i], (void *)&ret2);

        if (ret1 == NULL)
        { // this check might be redundant but idk

            continue;
        }

        if (ret2 == NULL)
        {

            ret2 = ret1;

            continue;
        }

        run *runs = ret1->runs;

        int *n = &ret1->n;

        if (runs[*n - 1].ch == ret2->runs[0].ch)
        {

            ret2->runs[0].reps += runs[*n - 1].reps;

            runs = realloc(runs, sizeof(run) * (*n - 1));

            *n -= 1;
        }

        fwrite(runs, sizeof(run), *n, stdout);

        free(ret1);
    }

    run *runs = ret2->runs;

    int n = ret2->n;

    fwrite(runs, sizeof(run), n - 1, stdout);

    holdout = &runs[n - 1]; // save the last run before writing it, so we can merge it with the next file if needed

    free(ret2);

    fflush(stdout);

    return holdout;
}

void *zip_t(void *args)
{

    argpack argstruct = *(argpack *)args;

    char *arr = argstruct.ptr;

    bound bd = argstruct.bd;

    free(args);

    return zip(arr, bd);
}

retpack *zip(char *str, bound bd)
{

    int size = bd.end - bd.start;

    int runs = 0;

    run *buf = malloc(size * sizeof(run));

    int chcount;

    int i = bd.start + 1;

    char ch, repch;

    ch = str[bd.start];

    while (ch == '\0' && i < bd.end)
    { // skip to first non-terminal

        ch = str[i++];
    }

    if (size == 1)
    {

        buf[0].reps = 1;

        buf[0].ch = ch;

        runs = 1;

        retpack *r = malloc(sizeof(retpack *));

        r->runs = buf;

        r->n = runs;

        return r;
    }

    while (i < bd.end)
    {

        repch = ch;

        chcount = 1;

        while (i < bd.end && ((ch = str[i++]) == repch || ch == '\0'))
        {

            if (ch != '\0')
            {

                chcount++;
            }
        }

        buf[runs].reps = chcount;

        buf[runs].ch = repch;

        runs++;
    }

    if (str[i - 1] != str[i - 2])
    {

        buf[runs].reps = 1;

        buf[runs].ch = str[i - 1];

        runs++;
    }

    if (runs == 0)
    {

        free(buf);

        return NULL;
    }

    buf = realloc(buf, runs * sizeof(run));

    retpack *r = malloc(sizeof(retpack));

    r->runs = buf;

    r->n = runs;

    return r;
}

/* Returns a list of bounds that will split an array of size size into n approximately equal chunks.

 * If size % n != 0, then the first size % n chunks will be one larger than the last n - (size % n).

 * Ex: for n = 3 and size = 60, returns [0, 20], [20, 40], [40, 60]. 

 *     for n = 3 and size = 58, returns [0, 20], [20, 39], [39, 58]. */

bound *get_even_bounds(int n, int size)
{

    // size = size + 1;

    bound *bounds = malloc(n * sizeof(bound));

    int leftover = size % n;

    int boundsize = size / n;

    int offset = 0;

    for (int currbound = 0; currbound < n; currbound++)
    {

        bounds[currbound].start = offset;

        bounds[currbound].end = (currbound < leftover) ? (offset + boundsize + 1) : (offset + boundsize);

        offset += (currbound < leftover) ? boundsize + 1 : boundsize;
    }

    return bounds;
}