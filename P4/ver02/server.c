#include <stdio.h>
#include "udp.h"
#define BLOCK_SIZE   (4096)
#define CHECKPOINT_SIZE (1028)
#define INODE_SIZE (64)
#define IMAP_SIZE (64)
#define BUFFER_SIZE (1000)
struct inode{

	int size;
	int type;// 1 if directory, 0 if file.
	int pointer[14];  
};

struct checkpoint{
	int end;
	int imap[256];
};

struct entry{
	int inodenumber;
	char name[28] ;
	
};

struct directory {
	struct entry entries[128];
};

struct imap{
	int inode_index[16];
};
struct message {
	int pointer;
	int val;
	char subject[1000];	
};
void initialization(int fp){
	struct checkpoint cr;
	cr.end=CHECKPOINT_SIZE+BLOCK_SIZE+INODE_SIZE+IMAP_SIZE;
	cr.imap[0]=CHECKPOINT_SIZE+BLOCK_SIZE+INODE_SIZE;
	struct imap im;
	im.inode_index[0]=CHECKPOINT_SIZE+BLOCK_SIZE;
	for(int i=1;i<16;i++){
		im.inode_index[i]=-1;
	}
	struct inode in;
	in.size=1;
	in.type=1;
	in.pointer[0]= CHECKPOINT_SIZE;
	for(int i=1;i<14;i++){
		in.pointer[i]=-1;
	}
	struct directory dr;
	dr.entries[0].inodenumber=0;
	strcpy(dr.entries[0].name, ".");
	dr.entries[1].inodenumber=0;
	strcpy(dr.entries[1].name, "..");
	for(int i=2;i<128;i++){
		dr.entries[i].inodenumber=-1;
		strcpy(dr.entries[i].name, "\0");
	}
	
	write(fp, &cr, CHECKPOINT_SIZE);
	write(fp, &dr, BLOCK_SIZE);
	write(fp, &in, INODE_SIZE);
	write(fp, &im, IMAP_SIZE);


}



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
		
		// File Exists
		if(access("fileImage.txt",F_OK)==0){
		 	
		} else {
			
		}
    }
    return 0; 
}
