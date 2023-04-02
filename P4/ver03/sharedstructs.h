
#define MFS_LOOKUP 101
#define MFS_STAT 102
#define MFS_WRITE 103        
#define MFS_READ 104        
#define MFS_CREAT 105        
#define MFS_UNLINK 106        
#define MFS_SHUTDOWN 107 
#define BLOCKSIZE 4096  // block size

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
   int type;
   int file_type;
   int inum;
   int pinum;
   int block;
   char name[64];
   char buffer[BLOCKSIZE];
};

struct response{ 
   int retval;
   int inum;
   char msg[16];
   MFS_Stat_t mfs_stat;
   char buffer[BLOCKSIZE];
};
    