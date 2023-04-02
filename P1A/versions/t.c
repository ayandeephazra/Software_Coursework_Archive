#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[])
{

    FILE *f = fopen("bob.txt", "w");

    char *command;
    char *key;
    char *value;
    char *token;

    for (int i = 0; i < argc; i++)
    {
        char *temp = argv[i];

        //char new[] = &temp;
        token = strtok(temp, ",");

        // command common to all
        command = token;
        printf("\n");
        printf("token\n");
        printf(command);
        // key
        if (token != NULL)
        {
            ///////////////////////////////////////////////////////////////////////
            /// todo not null, find the whole argument and subtract the command
            ///////////////////////////////////////////////////////////////////////
            token = strtok(temp, ",");
            key = token;
            printf(token);
        }

        // value

        if (token != NULL)
        {
            token = strtok(NULL, ",");
            value = token;
        }
    }

    // if clauses
    if (command[1] != '\0')
    {
        // error case
    }
    else if (command[0] == 'p')
    {
        char a[100];
        if (token != NULL)
        {
            strcat(a, token);
            if (key != NULL)
            {
                strcat(a, key);
                if (value != NULL)
                {
                    strcat(a, value);
                }
            }
        }

        fprintf(f, "%s", a);
    }
    else if (command[0] == 'g')
    {
    }
    else if (command[0] == 'd')
    {
    }
    else if (command[0] == 'c')
    {
    }
    else if (command[0] == 'a')
    {
    }
    else
    {
        // handle case
    }
    fclose(f);

    return 0;
}