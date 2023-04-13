#include <stdlib.h>
#include <stdio.h>
#include <unistd.h>
#include <sys/wait.h>
#include <unistd.h>
#include <string.h>
#include <fcntl.h>
#include <stdlib.h>
#include <stdarg.h>

FILE *retFilePointer(char *name)
{
    FILE *fpointer = fopen(name, "r+");
    if (fpointer == NULL)
    {
        char error_message[30] = "An error has occurred\n";
        write(STDERR_FILENO, error_message, strlen(error_message));
        _exit(1);
    }
    return fpointer;
}
void finalCompressedOutput(char *name, char *stream)
{
    FILE *fpointer ;//= fopen(name, "w+");
    freopen(name,"w+",fpointer);
    if (fpointer == NULL)
    {
        char error_message[30] = "An error has occurred\n";
        write(STDERR_FILENO, error_message, strlen(error_message));
        _exit(1);
    }
    fprintf(fpointer, "%s", stream);
    fclose(fpointer);
}
int main(int argc, char *argv[])
{
    // GOOD
    if (argc >= 4)
    {
        FILE *fp = retFilePointer(argv[1]);
        // for every new character
        int newCharCount = 0;
        // saveLastChar
        char charec[1];

        if (strcmp(argv[argc - 2], ">") == 0)
        {
            // REALLY GOOD
            while (1)
            {
                char current = fgetc(fp);
                if (current == EOF)
                {
                    char finishedString[200] = "";
                    char temp[1];
                    temp[0] = (char)newCharCount;
                    strcat(finishedString, temp);
                    strcat(finishedString, charec);
                    printf("%sdvfvev", finishedString);
                    finalCompressedOutput(argv[argc - 1], finishedString);
                    newCharCount = 0;
                    break;
                }
                else
                {
                    printf("%d\n", newCharCount);
                    charec[0] = current;
                    newCharCount++;
                }
            }
        }
        else
        {
            // ??????????????
        }
    }
    else
    {
        // BAD, TOO FEW ARGUMENTS
        // >> ./a xyz abc > comp.txt should give 4 args, more if more files are to be done
    }

    // printf("%d%s", argc, argv[argc - 1]);
}