#include <stdio.h>
#include "udp.h"

#define BUFFER_SIZE (1000)
struct inode{
	int size;
	int isRegular; // 0 is directory, 1 is regular file
	int [pointer[14];  
};

struct checkpoint{

	int end;
	int imap[256];
	char padding[3068];
};

struct imap{

	int inode_index[16];
};
struct message {
	int pointer;
	int val;
	char subject[1000];	
};
// server code
int main(int argc, char *argv[]) {
    int sd = UDP_Open(10000);
    assert(sd > -1);
    while (1) {
	struct sockaddr_in addr;
	struct message msg;
	printf("server:: waiting...\n");
	int rc = UDP_Read(sd, &addr, (char*)&msg, sizeof(struct message));
	
	//printf("server:: read message [size:%d contents:(%s)]\n", rc, message);
	if (rc > 0) {
            //char reply[BUFFER_SIZE];
            //sprintf(reply, "taco");
            printf("SERVER \n");
            printf("Message Pointer: %d\n",msg.pointer);
            printf("Message Value: %d\n",msg.val);
            printf("Message Subject: %s\n",msg.subject);
            rc = UDP_Write(sd, &addr, (char*)&msg, sizeof(struct message));
	    printf("server:: reply\n");
	} 
    }
    return 0; 
}
