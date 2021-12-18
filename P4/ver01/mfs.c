#include "mfs.h"
#include "udp.h"


struct sockaddr_in server;
struct sockaddr_in other;
int connfd;
int init_called = 0; // false if MFS_Init() has not been called 
message req; // request to server
response res; // response from server


int MFS_Init(char *hostname, int port){
  UDP_FillSockAddr(&server, hostname, port);
return 0;
}
int MFS_Lookup(int pinum, char *name){
return 0;
}
int MFS_Stat(int inum, MFS_Stat_t *m){
return 0;
}
int MFS_Write(int inum, char *buffer, int block){
return 0;
}
int MFS_Read(int inum, char *buffer, int block){
return 0;
}
int MFS_Creat(int pinum, int type, char *name){
return 0;
}
int MFS_Unlink(int pinum, char *name){
return 0;
}
int MFS_Shutdown(){
return 0;
}
