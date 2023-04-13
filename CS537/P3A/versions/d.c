#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

// if theres redirection somewhere in the file
int redirect = 0;
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

        //printf("%d%c",count,str[i]);
        fwrite(&count, 4, 1, stdout);
        fwrite(&str[i], 1, 1, stdout);
    }
    //printf("\n");
}

int pzip_multiple(char *str, int totalcount)
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
        totalcount = totalcount + count;
        //printf("%d%c",count,str[i]);
    }
    //printf("\n");
    return totalcount;
}

int main(int argc, char *argv[])
{
    redirect = 0;
    // NO ARGUMENTS!!!!!!!!111
    if (argc == 1)
    {
    }
    for (int i = 0; i < argc; i++)
    {
        if (strcmp(argv[i], ">") == 0)
            redirect = 1;
    }

    // multiple file case
    if ((argc > 4 & redirect == 1) | (argc > 2 & redirect == 0))
    {

        char *line;
        int count = 0;
        for (int i = 1; i < argc & redirect == 0; i++)
        {
            FILE *fpointer1 = fopen(argv[i], "r+");
            if (fpointer1 == NULL)
            {
                printf("pzip: file1 [file2 ...]\n");
                return 1;
            }

            while (1)
            {
                line = "";
                int length;
                size_t len = 0;
                length = getline(&line, &len, fpointer1);
                if (length == -1)
                {
                    exit(0);
                }
                count = pzip_multiple(line, count);
            }

            fclose(fpointer1);
        }
        printf("%d",count);
        fwrite(&count, 4, 1, stdout);
        fwrite(&line[argc-1], 1, 1, stdout);
        return 0;
    }
    // single file case
    else
    {
        FILE *fpointer = fopen(argv[1], "r+");
        if (fpointer == NULL)
        {
            printf("pzip: file1 [file2 ...]\n");
            return 1;
        }
        while (1)
        {
            char *line = NULL;
            int length;
            size_t len = 0;
            length = getline(&line, &len, fpointer);
            if (length == -1)
            {
                exit(0);
            }
            pzip(line);
        }
        fclose(fpointer);

        return 0;
    }
}
