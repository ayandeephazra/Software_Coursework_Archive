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

int NPROCS;
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
char **file;
void *current_addr = 0;
int num_thread;
struct node **newfile;
int *newfile_size;

struct input
{
    int index;
    int len;
    char *line;
};

struct limit
{
    int begin;
    int last;
    int size;
};


struct bottom
{
    char *pointer;
    struct limit body;
};

struct node
{
    int count;
    char let;
};

struct trial
{
    int cycles;
    char character;
} __attribute__((packed));
struct data
{
    struct trial *trials;
    int n;
};

struct limit *get_even_limits(int chunks, int size)
{
    struct limit *limits = malloc(chunks * sizeof(struct limit));
    int rem = size % chunks;
    int limitsize = size / chunks;
    int offset = 0;
    int i = 0;
    for (; i < chunks; i++)
    {
        limits[i].begin = offset;
        if (i >= rem)
        {
            limits[i].last = offset + limitsize;
        }
        else
        {
            limits[i].last = offset + limitsize + 1;
        }
        limits[i].size = limits[i].last - limits[i].begin;
        if (i >= rem)
        {
            offset = offset + limitsize;
        }
        else
        {
            offset = offset + limitsize + 1;
        }
    }
    return limits;
}

void *compress_deprecated(void *arguments, int season)
{
    struct input *input = (struct input *)arguments;
    long bottom = input->len;
    struct node *file = malloc(sizeof(struct node) * season);
    int begin = season;
    int index = 0;
    do
    {
        if (*(input->line + index) == 0)
        {
            index++;
            continue;
        }
        else
            break;
    } while (index < bottom);
    char character = *(input->line + index);
    int cc = 1;
    int ni = 0;
    index++;
    for (; index < bottom;)
    {
        if (*(input->line + index) == 0)
        {
            index++;
            continue;
        }
        if (character != *(input->line + index))
        {
            if (ni == begin)
            {
                begin = begin * 2;
                file = realloc(file, sizeof(struct node) * begin);
                if (file == NULL)
                {
                    printf("Thread %d realloc 2 in compress() failed\n", input->index);
                    exit(1);
                }
            }
            file[ni].count = cc;
            file[ni].let = character;
            character = *(input->line + index);
            cc = 1;
            ni++;
        }
        else
        {
            cc++;
        }
        index++;
    }
    file[ni].count = cc;
    file[ni].let = character;
    ni++;
    newfile[input->index] = malloc(sizeof(struct node) * ni);
    newfile_size[input->index] = ni;
    memcpy(newfile[input->index], file, sizeof(struct node) * ni);
    free(input);
    return NULL;
}

struct data *wzip(char *string, struct limit body)
{
    int index = 0;
    struct trial *marlo = malloc(body.size * sizeof(struct trial));
    int charcount;
    int begin = body.begin + 1;
    char character, total;
    character = string[body.begin];
    while (character == '\0' && begin < body.last)
    {
        character = string[begin++];
    }
    if (body.size == 1)
    {
        marlo[0].cycles = 1;
        marlo[0].character = character;
        index = 1;
        struct data *temp = malloc(sizeof(struct data *));
        temp->trials = marlo;
        temp->n = index;
        return temp;
    }
    while (begin < body.last)
    {
        total = character;
        charcount = 1;
        while (begin < body.last && ((character = string[begin++]) == total || character == '\0'))
        {
            if (character != '\0')
            {
                charcount++;
            }
        }
        marlo[index].cycles = charcount;
        marlo[index].character = total;
        index++;
    }
    if (string[begin - 1] != string[begin - 2])
    {
        marlo[index].cycles = 1;
        marlo[index].character = string[begin - 1];
        index++;
    }
    if (index == 0)
    {
        free(marlo);
        return NULL;
    }
    marlo = realloc(marlo, index * sizeof(struct trial));
    struct data *temp = malloc(sizeof(struct data));
    temp->trials = marlo;
    temp->n = index;
    return temp;
}

void pzip(char *str)
{

    int length = strlen(str);

    for (int i = 0; i < length; i++)
    {
        int count = 1;
        while (i < length - 1 && str[i] == str[i + 1])
        {
            count++;
            i++;
        }
        if (str[i] != '\n')
        {
            //printf("%d%c",count,str[i]);
            fwrite(&count, 4, 1, stdout);
            fwrite(&str[i], 1, 1, stdout);
        }
        else
        {
            //printf("%d\\n",count);
            char nextLine = '\n';
            fwrite(&count, 4, 1, stdout);
            fwrite(&nextLine, 1, 1, stdout);
        }
    }
    //printf("\n");
}

struct trial *generate(int nprocs, pthread_t *objects, struct trial *runnout)
{
    struct data *total1;
    struct data *total2;
    pthread_join(objects[0], (void *)&total2);
    int index = 1;
    while (total2 == NULL)
    {
        pthread_join(objects[index++], (void *)&total2);
    }
    if (runnout != NULL)
    {
        struct trial *begin = &total2->trials[0];
        if (runnout->character == begin->character)
        {
            begin->cycles = begin->cycles + runnout->cycles;
        }
        else
        {
            int *val = &total2->n;
            total2->trials = realloc(total2->trials, *val * (sizeof(struct trial) + 1));
            memmove(total2->trials + 1, total2->trials, *val * sizeof(struct trial));
            total2->trials[0] = *runnout;
            *val = *val + 1;
        }
    }
    for (; index < NPROCS; index++)
    {
        total1 = total2;
        pthread_join(objects[index], (void *)&total2);
        if (total2 == NULL)
        {
            total2 = total1;
            continue;
        }
        struct trial *max = total1->trials;
        int *val = &total1->n;
        if (max[*val - 1].character == total2->trials[0].character)
        {
            total2->trials[0].cycles = total2->trials[0].cycles + max[*val - 1].cycles;
            max = realloc(max, sizeof(struct trial) * (*val - 1));
            *val = *val - 1;
        }
        fwrite(max, sizeof(struct trial), *val, stdout);
        free(total1);
    }
    struct trial *max = total2->trials;
    int n = total2->n;
    fwrite(max, sizeof(struct trial), n - 1, stdout);
    runnout = &max[n - 1];
    free(total2);
    fflush(stdout);
    return runnout;
}

void *worker(void *argument)
{
    struct bottom typecastedBottom = *(struct bottom *)argument;
    struct limit body = typecastedBottom.body;
    char *string = typecastedBottom.pointer;

    free(argument);
    return wzip(string, body);
}

int main(int argc, char *argv[])
{

    if (argc < 2)
    {
        printf("pzip: file1 [file2 ...]\n");
        exit(1);
    }
    NPROCS = get_nprocs();
    int validfiles = 0;
    struct trial *runnout = NULL;
    for (int f = 1; f < argc; f++)
    {
        char *fileName = argv[f];
        int fd;
        if ((fd = open(fileName, O_RDONLY)) < 0)
        {
            continue;
        }
        validfiles++;
        struct stat statbuf;
        if (fstat(fd, &statbuf) < 0)
        {
            printf("Couldn't get file stats\n");
            exit(1);
        }
        char *pointer = mmap(NULL, statbuf.st_size, PROT_READ, MAP_SHARED, fd, 0);
        if (pointer == MAP_FAILED)
        {
            printf("Mapping failed\n");
            exit(1);
        }
        close(fd);
        struct limit *limits = get_even_limits(NPROCS, statbuf.st_size);
        pthread_t *threads = malloc(sizeof(pthread_t) * NPROCS);
        struct bottom *args;
        for (int i = 0; i < NPROCS; i++)
        {
            args = malloc(sizeof *args);
            args->pointer = pointer;
            args->body = limits[i];
            if (pthread_create(&threads[i], NULL, worker, args) != 0)
            {
                printf("Unable to create thread %d\n", i);
                exit(1);
            }
        }
        runnout = generate(NPROCS, threads, runnout);
        munmap(pointer, statbuf.st_size);
        free(limits);
        free(threads);
    }
    if (validfiles)
    {
        fwrite(runnout, sizeof(struct trial), 1, stdout);
    }
    return 0;
}
