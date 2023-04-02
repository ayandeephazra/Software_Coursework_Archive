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
        printf("%s,", (node->pair).key);
        printf("%s\n", (node->pair).value);
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

void add(Node n, Node curr)
{
    while (curr != NULL)
    {
        if (curr->next == NULL)
        {
            curr->next = n;
            //curr->pair = n->pair;
            //curr->next = NULL;
            n->next = NULL;
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

void del(char key[], Node curr)
{
    Node copy1 = curr, copy2 = curr, copy3 = curr;

    int nodeCount = -1;

    while (copy1 != NULL)
    {
        nodeCount++;
        copy1 = copy1->next;
    }

    int indexOfn = -1;
    while (copy2 != NULL)
    {
        indexOfn++;
        if (strcpy((copy2->pair).key, key) == 0)
        {
            break;
        }
        copy2 = copy2->next;
    }

    Node prev, next;

    for (int i = 0; i <= nodeCount; i++)
    {
        if (i == indexOfn - 1)
        {
            prev = copy3;
        }
        if (i == indexOfn + 1)
        {
            next = copy3;
        }
        copy3 = copy3->next;
    }

    prev->next = next;
}
int main(int argc, char *argv[])
{
    struct Node *start = NULL;
    loadTXT(&start);
    //ViewList(start);

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

        //printf("command:%s\n", command);
        //printf("key:%s\n", key);
        //printf("value:%s\n", value);
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
            FILE *f = fopen("bob.txt", "a+");
            fprintf(f, "%s,%s\n", key, value);
            fclose(f);
        }
        // GET COMMAND
        if (tokenNum == 2 && strcmpi(command, "g") == 0)
        {
            find(start, key);
        }
        // DEL COMMAND
        if (tokenNum == 2 && strcmpi(command, "d") == 0)
        {
            del(key, start);
            printf("cut");
            ViewList(start);
            Node runner = start;
            FILE *f = fopen("bob.txt", "w");
            while (runner != NULL)
            {
                printf("here");
                if (strcmp(key, (runner->pair).key) != 0)
                {
                    fprintf(f, "%s,%s\n", (runner->pair).key, (runner->pair).value);
                }

                runner = runner->next;
            }
            fclose(f);
        }
        // CLEAR COMMAND
        if (tokenNum == 1 && strcmpi(command, "c") == 0)
        {
            free(start);
            // fclose(f);
            FILE *f = fopen("bob.txt", "w");
            fclose(f);
            f = fopen("bob.txt", "a+");
            fclose(f);
        }
        // ALL COMMAND
        if (tokenNum == 1 && strcmpi(command, "a") == 0)
        {
            ViewList(start);
        }
    }

    return 0;
}