#include <ctype.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <sys/types.h>

int main(int argc, char *argv[])
{

    printf("wish> ");
    char str[500];
    while (1)
    {
        if (fgets(str, 500, stdin) != NULL)
        {
            
            break;
        }
    }

    return 0;
}