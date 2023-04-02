#define _XOPEN_SOURCE 700
#include <ctype.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/types.h>

#define PATHS_LEN 200
int nCurrentPaths = 1;
// variable that keeps tracks of redirection
int validRedirect = 0;
int currentRunPathInvoke = 0;
char paths[200][200];
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
                char error_message[30] = "An error has occurred\n";
                write(STDERR_FILENO, error_message, strlen(error_message));
                //printf("An error has occurred\n");
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

int non_built_in(int tokenCount, char commands[tokenCount][200])
{

    int rc = fork();
    if (rc == 0)
    {
        int pass = 0;
        char temp[200];

        for (int k = 0; k < nCurrentPaths; k++)
        {
            strcpy(temp, "");
            strcpy(temp, paths[k]);
            strcat(temp, "/");
            strcat(temp, commands[0]);
            int ret = access(temp, X_OK);
            if (ret != -1)
            {
                pass = 1;
                char *execarg[tokenCount + 1];
                //  strcpy(execarg[0], temp);
                for (int j = 0; j < tokenCount; j++)
                {
                    strcpy(execarg[j], commands[j]);
                }
                execarg[tokenCount] = NULL;
                return execv(strdup(temp), execarg);
            }
        }
        if (pass == 0)
        {
            char error_message[30] = "An error has occurred\n";
            write(STDERR_FILENO, error_message, strlen(error_message));
        }
        // commands[tokenCount] = '\0';
        //  printf("here%s,",temp);
        // printf("%i",tokenCount);

    //    char error_message[30] = "An error has occurred\n";
     //   write(STDERR_FILENO, error_message, strlen(error_message));
        exit(0);
    }
    else if (rc > 0)
    {
        wait(NULL);
    }
    //}
    return 0;
}

int cd_mode(int tokenCount, char commands[tokenCount][200])
{
    return chdir(commands[1]);
}

void path_mode(int tokenCount, char commands[tokenCount][200])
{
    for (int i = 1; i < tokenCount; i++)
    {
        strcpy(paths[i - 1], commands[i]);
    }
    nCurrentPaths = tokenCount - 1;
}
void loop_mode(int tokenCount, char commands[tokenCount][200])
{

    int count = atoi(commands[1]);
    if (count == -1)
    {
        char error_message[30] = "An error has occurred\n";
        write(STDERR_FILENO, error_message, strlen(error_message));
        exit(0);
    }
    int i = 0;
    while (i != 5)
    {

        i++;
    }
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
                int retExit = exit_mode(str, tokenCount);
                if (retExit == -1)
                    return -1;
            }
            else if (strcmp(commands[0], "cd") == 0)
            {
                if (tokenCount != 2)
                    printf("An error has occurred\n");
                if (cd_mode(tokenCount, commands) == -1)
                    printf("An error has occurred\n");
            }
            else if (strcmp(commands[0], "path") == 0)
            {
                path_mode(tokenCount, commands);
                // printf("were here path");
                for (int i = 0; i < nCurrentPaths; i++)
                {
                    printf("%i)%s\n", i, paths[i]);
                }
            }
            else
            {
                if (non_built_in(tokenCount, commands) == -1)
                {

                    char error_message[30] = "An error has occurred\n";
                    write(STDERR_FILENO, error_message, strlen(error_message));
                    continue;
                }
                else
                {
                    printf("hafeeeeeeee");
                }
            }

            break;
        }
    }
    return 0;
}

int batch_mode(int argc, char *argv[])
{
    //printf("%s",argv[1]);
    FILE *f;
    char *buffer;
    char str[200];
    // f = fopen(argv[1], "r");
    if (!(f = fopen(argv[1], "r")))
    {
        char error_message[30] = "An error has occurred\n";
        write(STDERR_FILENO, error_message, strlen(error_message));
        exit(1);
    }
    while (fgets(str, 500, f) != NULL)
    {

        //  if (fgets(str, 500, f) != NULL)
        // {
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
        int foundredirect = 0;
        for (int i = 0; i < tokenCount; i++)
        {
            if (strcmp(commands[i], ">"))
            {
                // checks the case
                if (foundredirect == 0 & i != 1)
                    foundredirect = 1;
                else
                {
                    char error_message[30] = "An error has occurred\n";
                    write(STDERR_FILENO, error_message, strlen(error_message));
                    exit(0);
                }
            }
        }

        if (foundredirect == 1)
        {
            if (strcmp(commands[tokenCount - 1], ">") == 0)
            {
                char error_message[30] = "An error has occurred\n";
                write(STDERR_FILENO, error_message, strlen(error_message));
                exit(0);
            }
            else
                validRedirect = 1;
        }

        //printf("test%stest",commands[0]);

        /////////////////////////////////////////////////////////
        if (strcmp(commands[0], "exit") == 0)
        {
            int retExit = exit_mode(str, tokenCount);
            if (retExit == -1)
                return -1;
        }
        else if (strcmp(commands[0], "cd") == 0)
        {
            if (tokenCount != 2 || cd_mode(tokenCount, commands) == -1)
            {
                char error_message[30] = "An error has occurred\n";
                write(STDERR_FILENO, error_message, strlen(error_message));
                exit(0);
            }

            // if (cd_mode(tokenCount, commands) == -1)
            //  printf("An error has occurred\n");
        }
        else if (strcmp(commands[0], "path") == 0)
        {
            currentRunPathInvoke = 1;
            path_mode(tokenCount, commands);
            // printf("were here path");
            for (int i = 0; i < nCurrentPaths; i++)
            {
            }
        }
        else if (strcmp(commands[0], "loop") == 0)
        {
            loop_mode(tokenCount, commands);

            for (int i = 0; i < nCurrentPaths; i++)
            {
                // printf("%i)%s\n", i, paths[i]);
            }
        }

        else
        {
            for(int i=0; i<strlen(commands[0]); i++){
                if(commands[0][i]=='.' && currentRunPathInvoke==0){
                    
                     char error_message[30] = "An error has occurred\n";
                    write(STDERR_FILENO, error_message, strlen(error_message));
                    exit(0);
                }
            }
     
            if (non_built_in(tokenCount, commands) == -1)

            {
                //char error_message[30] = "An error has occurred\n";
                //write(STDERR_FILENO, error_message, strlen(error_message));
                exit(0);
            }
        }

        break;
    }
    return 0;
}

int main(int argc, char *argv[])
{
    // initial path setting
    strcpy(paths[0], "/bin");
    //
    if (argc > 2)
    {
        char error_message[30] = "An error has occurred\n";
        write(STDERR_FILENO, error_message, strlen(error_message));
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
        while (1)
        {
            int ret = batch_mode(argc, argv);
            if (ret == -1)
            {
                char error_message[30] = "An error has occurred\n";
                write(STDERR_FILENO, error_message, strlen(error_message));
                exit(0);
            }
            else
                break;
        }
    }
    exit(0);

    return 0;
}