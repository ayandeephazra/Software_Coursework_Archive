#include <stdlib.h>
#include <stdio.h>
#include <string.h>

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
    printf("%s,%s\n", p.key, p.value);
}

void ViewList(Node node)
{
    while (node != NULL)
    {
        if (strcmp(node->pair.key, "") != 0 && strcmp(node->pair.key, "") != 0)
        {
            printf("%s,", (node->pair).key);
            printf("%s\n", (node->pair).value);
        }
        node = node->next;
    }
}
void find(Node node, char key[])
{
    while (node != NULL)
    {
        if (strcmp((node->pair).key, key) == 0)
        {
            printf("%s,", (node->pair).key);
            printf("%s\n", (node->pair).value);
            return;
        }
        node = node->next;
    }
    printf("%s not found\n", key);
}
void loadTXT(Node *pp)
{
    FILE *f;
    struct Pair p;
    char *buffer;
    if (!(f = fopen("bob.txt", "r+")))
    {
        perror("Error");
        exit(-1);
    }

    buffer = malloc(sizeof(struct Pair));

    while (fgets(buffer, sizeof(struct Pair), f))
    {
        char temp[200], key[100], value[100];
        if (sscanf(buffer, "%s", temp) > 0)
        {
            int tokenNum = 0;
            char *token = strtok(temp, ",");
            while (token != NULL)
            {
                if (tokenNum == 0)
                    strcpy(key, token);
                if (tokenNum == 1)
                    strcpy(value, token);
                token = strtok(NULL, ",");
                tokenNum++;
            }
            strcpy(p.key, key);
            strcpy(p.value, value);
            NewList(p, pp);
        }
    }

    free(buffer);
    fclose(f);
}
void add(Node n, Node curr)
{
    loadTXT(&curr);
    if ((curr == NULL) || (strcmp((curr->pair).key, "") == 0 && strcmp((curr->pair).value, "") == 0) || ((curr->pair).key == NULL && (curr->pair).value == NULL))
    {
        curr = (Node)malloc(sizeof(struct Node));

        curr->next = NULL;
        strcpy((curr->pair).key, (n->pair).key);
        strcpy((curr->pair).value, (n->pair).value);
      //curr->pair = n->pair;
        return;
    }
    while (curr != NULL)
    {
  
        if (curr->next == NULL)
        {
            curr->next = n;
            n->next = NULL;
        }
        curr = curr->next;
    }
}

int main(int argc, char *argv[])
{
    // HEAD of linked list
    struct Node *start = NULL;
    loadTXT(&start);
    char command[100], key[100], value[100], token2[100];

    for (int i = 1; i < argc; i++)
    {
        strcpy(command, ""); // must set to empty string every iteration
        strcpy(key, "");     // must set to empty string every iteration
        strcpy(value, "");   // must set to empty string every iteration
        int tokenNum = 0;    // we have commands of 1,2 and 3 tokens so this is necessary
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

        // PUT COMMAND
        if (tokenNum == 3)
        {
            struct Pair p;
            strcpy(p.key, key);
            strcpy(p.value, value);
            Node temp;

            temp = (Node)malloc(sizeof(struct Node));

            temp->pair = p;
            temp->next = NULL;

            add(temp, start);
        }
        // GET COMMAND
        if (tokenNum == 2 && strcmpi(command, "g") == 0)
        {
            find(start, key);
        }
        // CLEAR COMMAND
        if (tokenNum == 1 && strcmpi(command, "c") == 0)
        {
            start->next = NULL;
            strcpy(start->pair.key, "");
            strcpy(start->pair.value, "");
            free(start);

            FILE *fp = fopen("bob.txt", "w");
            fclose(fp);
        }
        // ALL COMMAND
        if (tokenNum == 1 && strcmpi(command, "a") == 0)
        {
            ViewList(start);
        }
    }
    FILE *f = fopen("bob.txt", "w");
    Node node = start;
    while (node != NULL)
    {
        //if (strcmp(node->pair.key, "") != 0 && strcmp(node->pair.key, "") != 0)
        fprintf(f, "%s,%s\n", (node->pair).key, (node->pair).value);
        node = node->next;
    }
    fclose(f);
    return 0;
}