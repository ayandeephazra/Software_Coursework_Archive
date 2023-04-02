#include <stdio.h>
#include "udp.h"

#define BUFFER_SIZE (1000)
struct message {
	int pointer;
	int val;
	char subject[1000];	
};
// client code
int main(int argc, char *argv[]) {
	struct message msg;
	
    struct sockaddr_in addrSnd, addrRcv;
	
    int sd = UDP_Open(20000);
    int rc = UDP_FillSockAddr(&addrSnd, "localhost", 10000);

    //sprintf(message, "hello world");

    //printf("client:: send message [%s]\n", message);
    msg.pointer=07;
    msg.val=19;
    strcpy(msg.subject, "Hello");
    rc = UDP_Write(sd, &addrSnd, (char*)&msg, sizeof(struct message));
    if (rc < 0) {
	printf("client:: failed to send\n");
	exit(1);
    }

    printf("client:: wait for reply...\n");
    rc = UDP_Read(sd, &addrRcv, (char*)&msg, sizeof(struct message));
    //printf("client:: got reply [size:%d contents:(%s)\n", rc, message);
    return 0;
}

