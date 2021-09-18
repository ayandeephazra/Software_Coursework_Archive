#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int main(int argc, char *argv[])
{

    FILE *f = fopen("bob.txt", "w");

    char command[] = "";
    char key[] = "";
    char value[] = "";
    char token[] = "";

    for (int i = 1; i < argc; i++)
    {
        command[0] = '\0';
        key[0] = '\0';
        value[0] = '\0';
        token[0] = '\0';
        //char *temp[100] = argv[i];
        char temp[100];
        strcpy(temp, argv[i]);
        printf("here\n");
        printf(temp);
        printf("\nend\n");

        int j = 0;
        char a[100]; // temporary variable that builds the key
        char b[100]; // temporary variable that builds the value
        int size = sizeof temp / sizeof temp[0];
        if (size == 1)
        {
            if (temp[0] != 'c' || temp[0] != 'a')
            {
                printf("illegal command");
            }
            else
            {
                // instead of command = temp[0]
                strncat(command, temp, 1);
            }
        }
        while (temp[j] != ',' && temp[j] != '\0')
        {
            if (j == 0)
            {
                strncat(command, temp, 1);
                // instead of command = temp[j], j = 0;
                printf(command);
                printf("here2\n");
                j++; continue;
            }
            if (j == 1)
            {
                if (temp[j] != ',')
                {
                    printf("error: wrong argument formatting");
                    break;
                }
                j++; continue;
            }
            printf("reacherd");

            char t[1] = {temp[j]};
            printf("\n%i", sizeof t/sizeof t[0]);
            printf("here%c",t[0]);
            strcat(a, t);

            j++;
        }
        strcpy(key, a);
        printf("key");
        printf(key);

        while (temp[j] != ',' || temp[j] != '\0')
        {
            char t[1] = {temp[j]};
            strcat(b, t); //strcat(b, temp[j]);

            j++;
        }
        strcpy(value, b);
        // instead of value = b;
    }

    
    printf(key);
    printf(value);
    printf(command);

    fclose(f);

    return 0;
}