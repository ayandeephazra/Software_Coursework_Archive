#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <stdbool.h>
#define HASHSIZE 200

struct Pair
{
    char key[100];
    char value[100];
};
typedef struct Node
{
    struct Pair pair;
    struct Node *next;
} TNode;

typedef TNode *Node;

void NewList(struct Pair p, Node *pp)
{
    Node temp;

    temp = (Node)malloc(sizeof(struct Node));

    temp->pair = p;
    temp->next = *pp;

    *pp = temp;
}
void ViewElement(struct Pair p)
{
    printf("%s %s\n", p.key, p.value);
}

void ViewList(Node node)
{
    while (node != NULL)
    {
        printf("%s %s\n", (node->pair).key, (node->pair).value);
        node = node->next;
    }
}

void add(Node n, Node curr){
    while (curr != NULL)
    {
        if(curr->next == NULL){
            curr = curr->next;
            curr = n;
        }
        curr = curr->next;
    }
}

void loadTXT(Node *pp)
{
    FILE *f;
    struct Pair p;
    char *buffer;
    if (!(f = fopen("bob.txt", "r")))
    {
        perror("Error");
        exit(-1);
    }

    buffer = malloc(sizeof(struct Pair));

    while (fgets(buffer, sizeof(struct Pair), f))
    {
        if (sscanf(buffer, "%s,%s", p.key, &p.value) > 0)
        {
            NewList(p, pp);
        }
    }

    free(buffer);
    fclose(f);
}
int main(int argc, char *argv[])
{
    struct Node *start = NULL;
    //LoadTXT(&start);
    ViewList(start);

    FILE *f = fopen("bob.txt", "a+");
    if (f == NULL)
        exit(EXIT_FAILURE);
    char command[100], key[100], value[100], token2[100];

    for (int i = 1; i < argc; i++)
    {
        strcpy(command, "");
        strcpy(key, "");
        strcpy(value, "");
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

        if (!((strcmp(command, "p") == 0 && tokenNum == 3) || (strcmp(command, "d") == 0 && tokenNum == 2) || (strcmp(command, "g") == 0 && tokenNum == 2) || (strcmp(command, "a") == 0 && tokenNum == 1) || (strcmp(command, "c") == 0 && tokenNum == 1)))
        {
            printf("bad commmand\n");
            continue;
        }

        printf("command:%s\n", command);
        printf("key:%s\n", key);
        printf("value:%s\n", value);

        if (tokenNum == 3)
        {
             printf("value:%i", tokenNum);
            //printf("bruh%s%s", key, value);
            //fprintf(f, "%s,%s\n", key, value);
            //printf("bruh");
            Node temp;
            temp->next = NULL;
            strcpy(temp->pair.key, key);
            strcpy(temp->pair.value, value);

            //add(temp, start);
            ViewList(start);
            printf("wtf");
        }

        // TODO CHANGE DATA STRCUTURE HERE AS WELL
        if (tokenNum == 1 && strcmpi(command, "c") == 0)
        {
            fclose(f);
            f = fopen("bob.txt", "w");
            if (f == NULL)
                exit(EXIT_FAILURE);
            fclose(f);
            f = fopen("bob.txt", "a+");
            if (f == NULL)
                exit(EXIT_FAILURE);

           // (start->pair).key = NULL;
           // (start->pair).value = NULL;
            strcpy(start->pair.key, NULL);
            strcpy(start->pair.value, NULL);
            start->next = NULL;

            printf("ggggg");
            ViewList(start);
        }
    }

    fclose(f);
    return 0;
}
/*
if(sscanf(buffer, "%s", temp)>0){
           int i = 0;
           while(temp[i]!='\0' && temp[i]!=','){
               strncat(key, &temp[i], 1);
               i++;
           }
           while(temp[i]!='\0'){
               strncat(value, &temp[i], 1);
               i++;
           }
           printf("here%s", key);
           strcpy(p.key, key);
           strcpy(p.value, value);
           NewList(p, pp);
       }

       */