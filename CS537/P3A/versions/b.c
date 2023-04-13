#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdarg.h>
#define _OPEN_SYS_ITOA_EXT
void pzip(char *str, char *dest)
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
        FILE * fp ;//= fopen(dest, "wb+");
        freopen(dest, "wb", fp);
        printf("%d%c", count, str[i]);
        //char *temp = ;
        char *line = "";
        int a = count%10;
        while(count!=0){
            char *temp = a + "0";
            
            strcat(line, temp);
            a = a/10;
        }
        
       // strcat(line, temp);
        strcat(line, &str[i]);
        fwrite(line, sizeof(line), 5, fp);
    }
    printf("\n");
}

int main(int argc, char *argv[])
{
    FILE *fpointer = fopen(argv[1], "r+");
    while (1)
    {
        char *line = NULL;
        int length;
        size_t len = 0;
        // destination file
        char *dest = argv[argc - 1];
        length = getline(&line, &len, fpointer);
        if (length == -1)
        {
            exit(0);
        }
        pzip(line, dest);
    }
    return 0;
}
