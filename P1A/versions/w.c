#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#define HASHSIZE 200
////////////////////////////////////////////////////////
struct DataItem {
   char key[100];   
   char value[100];
};

int hashCode(char key[]) {
    int keyint = atoi(key);
   return keyint % HASHSIZE;
}

void insert(char key[],int value[]) {}

struct DataItem* delete(struct DataItem* item) {}

void display() {}

struct DataItem *search(char key[]) {}

///////////////////////////////////////////////////////////

int main(int argc, char *argv[])
{
    FILE *f = fopen("bob.txt", "a+");
    char command[100], key[100], value[100], token2[100];

    for (int i = 1; i < argc; i++)
    {
        strcpy(command, ""); strcpy(key, ""); strcpy(value, "");
        int tokenNum = 0;
        char string[50];
        strcpy(string, argv[i]);
        // Extract the first token
        char *token = strtok(string, ",");

        // loop through the string to extract all other tokens
        while (token != NULL)
        {
            //printf(" %s\n", token); //printing each token
            if (tokenNum == 0)
                strcpy(command, token);
            if (tokenNum == 1)
                strcpy(key, token);
            if (tokenNum == 2)
                strcpy(value, token);
            token = strtok(NULL, ",");
            tokenNum++;
        }
        // so no funny business with junk values ensues
        if (tokenNum == 2)
            strcpy(value, "");
        if (tokenNum == 1)
            strcpy(key, "") && strcpy(value, "");

        if(!((strcmp(command, "p")==0 && tokenNum == 3 )|| (strcmp(command, "d")==0 && tokenNum == 2 )
        || (strcmp(command, "g")==0 && tokenNum == 2 ) || (strcmp(command, "a")==0 && tokenNum == 1 )
        || (strcmp(command, "c")==0 && tokenNum == 1 ))){
            printf("bad commmand\n");
            continue;
        }

        //printf("command:%s\n", command);
        //printf("key:%s\n", key);
        //printf("value:%s\n", value);

        if(tokenNum == 3) fprintf(f, "%s,%s\n", key, value);
        // TODO CHANGE DATA STRCUTURE HERE AS WELL
        if(tokenNum == 1 && strcmpi(command, "c")==0){
            fclose(f);
            f = fopen("bob.txt", "w");
            fclose(f);
            f = fopen("bob.txt", "a+");
        }
    }


    fclose(f);
    return 0;
}