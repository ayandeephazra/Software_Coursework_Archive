#define _XOPEN_SOURCE 700
#include <ctype.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
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
    return 0;
}

int ls_mode(char str[], int tokenCount)
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
        if (strcmp(commandToken, "cd") != 0 && strcmp(commandToken, "exit") != 0 && strcmp(commandToken, "path") != 0)
        {
            char *cmd_argv[20];
            cmd_argv[0] = strdup("/bin/ls");
            cmd_argv[1] = NULL;

            int rc = fork();

            /*     if (commandCount != tokenCount)
            {
                printf("An error has occurred\n");
                return -1;
            } */
            //  else
            //  {
            if (rc == 0)
            {
                return execv(cmd_argv[0], cmd_argv);
            }
            //return -1;
            // }
        }
        commandToken = strtok(NULL, " ");
    }
    return 0;
}

void cd_mode(char str[], int tokenCount)
{
    char *commandStr = strdup(str);
    strcat(commandStr, "  ");
    char *commandToken;
    int commandCount = 0;
    commandToken = strtok(commandStr, " \n");

    while (commandToken != NULL)
    {
        commandCount++;
        if (strcmp(commandToken, "cd") == 0)
        {
            char *dir = "\"";
            strcat(dir, commandToken);
            // printf(dir);

            if (chdir(dir) != 0)
            {
                printf("An error has occurred\n");
            }
        }
        commandToken = strtok(NULL, " ");
    }
    //  return 0;
}
int interactive_mode()
{

    char str[500];
    printf("wish> ");
    while (1)
    {
        if (fgets(str, 500, stdin) != NULL)
        {
            char *countStr = strdup(str);
            char *cdStr = strdup(str);
            char *forStr = strdup(str);
            char *countToken, *forToken;
            int tokenCount = 0;

            countToken = strtok(countStr, " ");
            while (countToken != NULL) // && countToken[0] != '\0'
            {
                tokenCount++;
                countToken = strtok(NULL, " ");
            }

            // printf("%i", tokenCount);
            if (tokenCount <= 0)
                continue;

            char commands[tokenCount][200];
            forToken = strtok(forStr, " ");
            for (int i = 0; i < tokenCount; i++)
            {
                forToken[strcspn(forToken, "\n")] = '\0';
                strcpy(commands[i], forToken);
                forToken = strtok(NULL, " ");
            }

            //printf("test%stest",commands[0]);

            /////////////////////////////////////////////////////////
            if (strcmp(commands[0], "exit") == 0)
            {

                //     printf("were here exit");
                int retExit = exit_mode(str, tokenCount);
                if (retExit == -1)
                {
                    return -1;
                }
            }
            else if (strcmp(commands[0], "cd") == 0)
            {
                printf("were her7e");
                cd_mode(cdStr, tokenCount);
            }
            else if (strcmp(commands[0], "path") == 0)
            {
                printf("were here path");
            }
            else
            {

                if (ls_mode(str, tokenCount) == -1)
                {
                    printf("An error has occurred\n");
                }
            }

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
        while (1)
        {
            int ret = interactive_mode();
            if (ret == -1)
                exit(0);
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