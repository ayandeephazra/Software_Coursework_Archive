#define _XOPEN_SOURCE 700
#include <ctype.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <process.h>
#include <sys/stat.h>
#include <sys/types.h>

int exit_mode(char str[], int tokenCount)
{
    //////////////////////////////////////////////////////////////
    char *commandStr = strdup(str);
    strcat(commandStr, "  ");
    char *commandToken;
    int commandCount = 0;
    commandToken = strtok(commandStr, " \n");

    while (commandToken != NULL)
    {
        commandCount++;
        if (strcmp(commandToken, "exit") == 0)
        {
            if (commandCount != tokenCount)
            {
                printf("An error has occurred\n");
                return -1;
            }
            else
            {
                return -1;
            }
        }
        commandToken = strtok(NULL, " ");
    }
}

int ls_mode()
{
    return 0;
}

int interactive_mode()
{
    printf("wish> ");
    char str[500];
    while (1)
    {
        if (fgets(str, 500, stdin) != NULL)
        {
            char *countStr = strdup(str);

            char *countToken;
            int tokenCount = 0;

            countToken = strtok(countStr, " ");
            while (countToken != NULL && countToken[0] != '\0')
            {
                tokenCount++;
                //printf("%s\n", countToken);
                countToken = strtok(NULL, " \n");
            }

            return exit_mode(str, tokenCount);

            break;
        }
    }
    return 0;
}

void batch_mode(char *argv[])
{
    printf("reached batch");
}

int main(int argc, char *argv[])
{
    if (argc > 2)
    {
        printf("An error has occurred\n");
        exit(1);
    }
    // interactive
    if (argc == 1)
    {

        char *cmd_argv[20];
        cmd_argv[0] = strdup("/bin/ls");
        cmd_argv[1] = NULL;
        while (1)
        {
            int rc = fork();

            if (rc == 0)
            {
                execv(cmd_argv[0], cmd_argv);
            }
            int ret = interactive_mode();
            if (ret == -1)
            {
                exit(0);
            }
        }
        exit(0);
    }
    //batch
    if (argc == 2)
    {
        batch_mode(argv);
    }

    return 0;
}