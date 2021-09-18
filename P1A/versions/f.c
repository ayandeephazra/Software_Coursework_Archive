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

            FILE *f = fopen("bob.txt", "a+");
            add(temp, start);
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
            // USE KEY TO DELETE LINE
            FILE *f, *fp;
            if (!(f = fopen("bob.txt", "a+")) || !(fp = fopen("temp.txt", "w+")))
            {
                perror("Error");
                exit(-1);
            }
            char *buffer;
            buffer = malloc(sizeof(struct Pair));
            while (fgets(buffer, sizeof(struct Pair), f))
            {
                char temp[200], keyT[100], valueT[100];
                if (sscanf(buffer, "%s", temp) > 0)
                {
                    int tokenNum = 0;
                    char *token = strtok(temp, ",");
                    while (token != NULL)
                    {
                        if (tokenNum == 0)
                            strcpy(keyT, token);
                        if (tokenNum == 1)
                            strcpy(valueT, token);
                        token = strtok(NULL, ",");
                        tokenNum++;
                    }
                }
                if (strcmp(key, keyT) != 0)
                {
                    fprintf(fp, "%s,%s\n", keyT, valueT);
                }
            }
            fclose(f);
            if (remove("bob.txt") == 0)
                printf("File deleted successfully");
            FILE *copier = fopen("bob.txt", "w");
            char ch;
            fseek(fp, 0, SEEK_SET);
            while ((ch = fgetc(fp)) != EOF)
                fputc(ch, copier);
            /*
            int ret = rename("temp.txt", "bob.txt");
            if (ret == 0)
            {
                printf("File renamed successfully");
            }
            else
            {
                printf("Error: unable to rename the file");
                fprintf(stderr, "System error (%d): %s\n", errno, strerror(errno));
            }
            */
            fclose(fp);
            fclose(copier);
            loadTXT(&start);

            free(buffer);
        }
        // CLEAR COMMAND
        if (tokenNum == 1 && strcmpi(command, "c") == 0)
        {
            free(start);
            FILE *f = fopen("bob.txt", "a+");
            fclose(f);
            f = fopen("bob.txt", "w");
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

    free(start);

    return 0;
}