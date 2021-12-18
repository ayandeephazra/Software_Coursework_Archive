#include <stdio.h>
#include<unistd.h> 
#include<fcntl.h> 
#include<stdlib.h>
#include<string.h>
#define BLOCK_SIZE   (4096)
#define CHECKPOINT_SIZE (1028)
#define INODE_SIZE (64)
#define IMAP_SIZE (64)
#define BUFFER_SIZE (1000)
struct inode{

	int size;
	int type;// 0 if directory, 1 if file.
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

struct inodeMapping {
	int inodes[4096];

};

struct inodeMapping inodeMapping;

int getFirstInode(){
	for (int i=0;i<4096;i++){
		if(inodeMapping.inodes[i]==-1){
			inodeMapping.inodes[i]=0;
			return i;
		}
	}
	return -1;
	
}





void createFile(int fp, char *str, int pinum){
	lseek(fp,0, SEEK_SET);
	struct checkpoint temp;
	read(fp, &temp, CHECKPOINT_SIZE);
	
	
	
	struct imap parentImap;
	int imapIndex=pinum/16;
	lseek(fp, temp.imap[imapIndex], SEEK_SET);
	read(fp, &parentImap, IMAP_SIZE);
	
	
	
	
	struct inode parentInode;
	int inodeIndex= pinum%16;
	lseek(fp, parentImap.inode_index[inodeIndex], SEEK_SET);
	read(fp, &parentInode, INODE_SIZE);
	
	struct directory parentDirectory;
	lseek(fp, parentInode.pointer[inodeIndex], SEEK_SET);
	read(fp, &parentDirectory, BLOCK_SIZE);
	
	//printf("PARENT DIRECTORY NAME :- %s\n",parentDirectory.entries[0].name);
	int inodeNum=getFirstInode();
	printf("INODE NUM IS BABY : %d\n",inodeNum);
	if(inodeNum==-1){
		return;
	}	
	// Update the Parent's Directory.
	for(int i=2;i<128;i++){
		if(parentDirectory.entries[i].inodenumber==-1){
			parentDirectory.entries[i].inodenumber=inodeNum;
			strcpy(parentDirectory.entries[i].name,str);
			break;
		}	
	}
	
	//printf("PARENT DIRECTORY NAME :- %s\n",parentDirectory.entries[0].name);
	
	// Current Inode Number
	// Parent's Inode Number
	
	lseek(fp,temp.end, SEEK_SET);
	// Writes the new Directory to memory.
	
	
	struct inode childInode;
	
	childInode.size = 0;
	childInode.type = 1;
	
	for(int i=0;i<14;i++){
		childInode.pointer[i]=-1;
	}
	// Writes the child Inode to memory. 
	write(fp, &childInode, INODE_SIZE);
	// Stores the childInode Address to memory.
    int childInodeAddress = temp.end;
    temp.end= temp.end+ INODE_SIZE;
	
	// Writes the new updated parent directory to memory.
    write(fp, &parentDirectory, BLOCK_SIZE);
    int newParentAddress= temp.end;
    temp.end=temp.end+ BLOCK_SIZE;
    
    // Updates the Parent Inode
    parentInode.size=parentInode.size+1;
    parentInode.pointer[inodeIndex]=newParentAddress;
    write(fp, &parentInode, INODE_SIZE);
    
    
    // Stores the Parent's Inode Address
	int newParentInodeAddress=temp.end;
	temp.end=temp.end+INODE_SIZE;
	
	
	parentImap.inode_index[imapIndex]=newParentInodeAddress;
	int boolean=0;
	if(imapIndex==15){
		boolean++;
	}
	if(boolean==0){
		parentImap.inode_index[imapIndex+1]=childInodeAddress;
	}
	
	write(fp, &parentImap, IMAP_SIZE);
    
    
    temp.imap[imapIndex]=temp.end;
	temp.end = temp.end + IMAP_SIZE; 
	fsync(fp);
	lseek(fp,0,SEEK_SET);
	write(fp, &temp, CHECKPOINT_SIZE);
}







void createDir(int fp, char *str, int pinum){

	



	lseek(fp,0, SEEK_SET);
	struct checkpoint temp;
	read(fp, &temp, CHECKPOINT_SIZE);
	
	
	struct imap parentImap;
	int imapIndex=pinum/16;
	lseek(fp, temp.imap[imapIndex], SEEK_SET);
	read(fp, &parentImap, IMAP_SIZE);
	
	struct inode parentInode;
	int inodeIndex= pinum%16;
	lseek(fp, parentImap.inode_index[inodeIndex], SEEK_SET);
	read(fp, &parentInode, INODE_SIZE);
	
	struct directory parentDirectory;
	lseek(fp, parentInode.pointer[inodeIndex], SEEK_SET);
	read(fp, &parentDirectory, BLOCK_SIZE);
	int inodeNum=getFirstInode();
	if(inodeNum==-1){
		return;
	}
	//printf("PARENT DIRECTORY NAME :- %s\n",parentDirectory.entries[0].name);
	
	// Update the Parent's Directory.
	for(int i=2;i<128;i++){
		if(parentDirectory.entries[i].inodenumber==-1){
			parentDirectory.entries[i].inodenumber=inodeNum;
			strcpy(parentDirectory.entries[i].name,str);
			break;
		}	
	}
	
	//printf("PARENT DIRECTORY NAME :- %s\n",parentDirectory.entries[0].name);
	
	struct directory childDir;
	// Current Inode Number
	childDir.entries[0].inodenumber=inodeNum;
	strcpy(childDir.entries[0].name,str);
	// Parent's Inode Number
	childDir.entries[1].inodenumber=0;
	strcpy(childDir.entries[1].name,parentDirectory.entries[0].name);
	for(int i=2;i<128;i++){
		childDir.entries[i].inodenumber=-1;
		strcpy(childDir.entries[i].name, "\0");
	}
	lseek(fp,temp.end, SEEK_SET);
	// Writes the new Directory to memory.
	write(fp, &childDir, BLOCK_SIZE);
	
	
	struct inode childInode;
	
	childInode.size = 2;
	childInode.type = 0;
	childInode.pointer[0] = temp.end;
	for(int i=1;i<14;i++){
		childInode.pointer[i]=-1;
	}
	// Writes the child Inode to memory. 
	write(fp, &childInode, INODE_SIZE);
	// Stores the childInode Address to memory.
    int childInodeAddress = temp.end + BLOCK_SIZE;
    temp.end= temp.end+ BLOCK_SIZE+ INODE_SIZE;
	
	// Writes the new updated parent directory to memory.
    write(fp, &parentDirectory, BLOCK_SIZE);
    int newParentAddress= temp.end;
    temp.end=temp.end+ BLOCK_SIZE;
    
    // Updates the Parent Inode
    parentInode.size=parentInode.size+1;
    parentInode.pointer[inodeIndex]=newParentAddress;
    write(fp, &parentInode, INODE_SIZE);
    
    
    // Stores the Parent's Inode Address
	int newParentInodeAddress=temp.end;
	temp.end=temp.end+INODE_SIZE;
	
	
	parentImap.inode_index[imapIndex]=newParentInodeAddress;
	int boolean=0;
	if(imapIndex==15){
		boolean++;
	}
	if(boolean==0){
		parentImap.inode_index[imapIndex+1]=childInodeAddress;
	}
	
	write(fp, &parentImap, IMAP_SIZE);
    
    
    temp.imap[imapIndex]=temp.end;
	temp.end = temp.end + IMAP_SIZE; 
	fsync(fp);
	lseek(fp,0,SEEK_SET);
	write(fp, &temp, CHECKPOINT_SIZE);
    
	
}

void initialization(int fp){
	
	struct checkpoint cr;
	cr.end=CHECKPOINT_SIZE+BLOCK_SIZE+INODE_SIZE+IMAP_SIZE;
	cr.imap[0]=CHECKPOINT_SIZE+BLOCK_SIZE+INODE_SIZE;
	for(int i=1;i<256;i++){
		cr.imap[i]=-1;
	}
	
	struct imap im;
	im.inode_index[0]=CHECKPOINT_SIZE+BLOCK_SIZE;
	for(int i=1;i<16;i++){
		im.inode_index[i]=-1;
	}
	struct inode in;
	in.size=1;
	in.type=0;
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
	inodeMapping.inodes[0]=0;
	for(int i=1;i<4096;i++){
		inodeMapping.inodes[i]=-1;		
	}
	lseek(fp,0,SEEK_SET);
	
	
	write(fp, &cr, CHECKPOINT_SIZE);
	write(fp, &dr, BLOCK_SIZE);
	write(fp, &in, INODE_SIZE);
	write(fp, &im, IMAP_SIZE);
	
	lseek(fp, 0, SEEK_SET);
	
	read(fp, &cr, CHECKPOINT_SIZE);

}

int lookup(int fp, int pinum, char*name){
	lseek(fp,0, SEEK_SET);
	struct checkpoint temp;
	read(fp, &temp, CHECKPOINT_SIZE);
	
	struct imap parentImap;
	int imapIndex=pinum/16;
	lseek(fp, temp.imap[imapIndex], SEEK_SET);
	read(fp, &parentImap, IMAP_SIZE);
	
	struct inode parentInode;
	int inodeIndex= pinum%16;
	lseek(fp, parentImap.inode_index[inodeIndex], SEEK_SET);
	read(fp, &parentInode, INODE_SIZE);
	
	if(parentInode.type!=0){
		return -1;
	}
	
	struct directory parentDirectory;
	lseek(fp, parentInode.pointer[inodeIndex], SEEK_SET);
	read(fp, &parentDirectory, BLOCK_SIZE);
	
	
	for(int i=2;i<128;i++){
		if(strcmp(parentDirectory.entries[i].name, name)==0){
			return parentDirectory.entries[i].inodenumber;
		}
	}
	return -1;
}
int writeFile(int fp, int inum,  char *buffer, int block){

	if(block>=14){
		//printf("HI 1\n");
		return -1;
	} 
	if(block<0){
		//printf("HI 2\n");
		return -1;
	}

	lseek(fp,0, SEEK_SET);
	struct checkpoint header;
	read(fp,&header,CHECKPOINT_SIZE);
	//printf("NISHIT END OF FILE IS : %d\n",header.end);
	struct imap imap;
	int imapIndex = inum/16;
	if(imapIndex>=256){
		//printf("HI 3\n");
		return -1;
	}
	if(imapIndex<0){
		//printf("HI 4\n");
		return -1;
	}
	
	lseek(fp, header.imap[imapIndex], SEEK_SET); 
	read(fp, &imap, IMAP_SIZE);
	
	struct inode inode;
	int inodeIndex= inum%16;
	if(inodeIndex>=16){
		//printf("HI 5\n");
		return -1;
	}
	if(inodeIndex<0){
		//printf("HI 6\n");
		return -1;
	}
	lseek(fp, imap.inode_index[inodeIndex], SEEK_SET);
	read(fp, &inode, INODE_SIZE);
	
	if(inode.type!=1){
		//printf("HI 7\n");
		return -1;
	}
	inode.pointer[block] = header.end;
	lseek(fp,header.end,SEEK_SET);
	
	// Write the Data Block
	write(fp, buffer, BLOCK_SIZE);
	header.end=header.end+BLOCK_SIZE;
	
	int newInodeAddress=header.end;
	
	// Write the updated INode
	write(fp, &inode, INODE_SIZE);
	header.end=header.end+INODE_SIZE;
	//printf("NISHIT END OF FILE IS : %d\n",header.end);
	int newImapAddress=header.end;
	imap.inode_index[inodeIndex]=newInodeAddress;
	write(fp, &imap, IMAP_SIZE);
	header.end=header.end+IMAP_SIZE;
	
	header.imap[imapIndex]=newImapAddress;
	lseek(fp,0,SEEK_SET);
	write(fp,&header, CHECKPOINT_SIZE);
	lseek(fp,0,SEEK_SET);
	read(fp,&header, CHECKPOINT_SIZE);
	
	return 0;
	
}

int readFile(int fp, int inum, char *buffer, int block){
	if(block>=14){
		return -1;
	} 
	if(block<0){
		return -1;
	}

	lseek(fp,0, SEEK_SET);
	struct checkpoint header;
	read(fp,&header,CHECKPOINT_SIZE);
	
	struct imap imap;
	int imapIndex = inum/16;
	if(imapIndex>=256){
		return -1;
	}
	if(imapIndex<0){
		return -1;
	}
	
	lseek(fp, header.imap[imapIndex], SEEK_SET); 
	read(fp, &imap, IMAP_SIZE);
	
	struct inode inode;
	int inodeIndex= inum%16;
	if(inodeIndex>=16){
		return -1;
	}
	if(inodeIndex<0){
		return -1;
	}
	lseek(fp, imap.inode_index[inodeIndex], SEEK_SET);
	// Read the Inode inside the struct.
	read(fp, &inode, INODE_SIZE);
	
	if(inode.pointer[block]==-1){
		return -1;
	}
	
	lseek(fp, inode.pointer[block], SEEK_SET);
	read(fp, buffer, BLOCK_SIZE); 		
	return 0;

}
int unlinkFiles(int fd, int pinum, char *name){
	struct checkpoint cr;
	lseek(fp,0,SEEK_SET);
	read(fp,&cr,CHECKPOINT_SIZE);
	
	if(pinum<0){
		return -1;
	}
	if(pinum>=4096){
		return -1;
	}
	
	int imapAddress = pinum/256;
 
	if(cr.pointer[imapAddress]==-1){
		return -1;
	}
	
	struct imap im;
	lseek(fp,cr.pointer[imapAddress],SEEK_SET);
	read(fp, &im, IMAP_SIZE);
	
	int inodeAddress = pinum%16;
	
	if(im.inode_index[inodeAddress]==-1){
		return -1;
	}
	
	struct inode in;
	
	lseek(fp, im.inode_index[inodeAddress],SEEK_SET); 
	read(fp, &in, INODE_SIZE);
	
	if(in.type!=0){
		return -1;
	}
	
	
	int inodeFound=lookup(fd, pinum, name);
	
	if(inodeFound==-1){
		return 0;
	}	
	struct checkpoint child;
	lseek(fp, 0, SEEK_SET);
	read(fp, &child, CHECKPOINT_SIZE);
	
	imapAddressChild= inodeFound/256;
	
	struct imap childImap;
	lseek(fp, child.imap[imapAddressChild], SEEK_SET);
	read(fp, &childImap, IMAP_SIZE);
	
	inodeAddressChild= inodeFound%16;
	
	struct inode childInode;
	lseek(fp, childImap.inode_index[inodeAddressChild], SEEK_SET);
	read(fp, &childInode, INODE_SIZE);
	
	if(childInode.type==1){
		// It's a FILE
		for(int i=0;i<14;i++){
			childInode.pointer[i]=-1;
		}
		inodeMapping.inodes[inodeNum]=-1;
		
	} else {
		// It's a Directory
		
		for(int i=0;i<14;i++){
			struct directory dr;
			lseek(fp, childInode.pointer[i], SEEK_SET);
			read(fp, &dr, BLOCK_SIZE);
			for(int j=2; j<128; j++){
				if(dr.entries[j].inodenumber!=-1){
					return -1;
				}	
			}
		}
		
		
		
		
		
	}
	
	childImap.inode_index[inodeAddressChild]=-1;
	lseek(fp, child.end, SEEK_SET);
	write(fp, &childImap, IMAP_SIZE);
	child.imap[imapAddressChild]=child.end;
	child.end=child.end+IMAP_SIZE;
	lseek(fp,0, SEEK_SET);
	write(fp, &child, CHECKPOINT_SIZE);
	
	int i=0;
	for(i=0;i<14;i++){
		struct directory parentDirectory;
	}
	
	in.inode_index[in]
	
	
	
	

	return 0;
}



void fstat(){


}

 


// server code
int main(int argc, char *argv[]) {

   int fd = open("foo.txt", O_RDWR | O_CREAT, 0644); 
   initialization(fd);
   lseek(fd, 0, SEEK_SET);
   
   createDir(fd, "Hello", 0);
   createFile(fd, "TacoSeason", 0);
   
   
   char *buffer=malloc(sizeof(char)*4096);
   char *dest=malloc(sizeof(char)*4096);
   strcpy(buffer,"Hello");
   
   
   
   
   writeFile(fd, 1, buffer, 0);
   
   struct checkpoint cr;
   lseek(fd,0,SEEK_SET);
   
   read(fd, &cr, CHECKPOINT_SIZE);
   
   //printf("y return count is %d\n",y);
   
   //printf("FILE'S END OF FILE IS : %d\n",cr.end);
   int imapAddress=cr.imap[0];
   lseek(fd, imapAddress, SEEK_SET);
   
   struct imap im;
   read(fd, &im, IMAP_SIZE);
   int inodeAddress=im.inode_index[2];
   lseek(fd, inodeAddress, SEEK_SET);
   
   struct inode in;
   read(fd, &in, INODE_SIZE);
   int blockAddress=in.pointer[0];
   lseek(fd, blockAddress, SEEK_SET);
   char taco[4096];
   read(fd, taco, BLOCK_SIZE);
   //printf("TACO IS %s\n",taco);
   
   
   readFile(fd, 2, dest, 0);
   
   
   
   
   
   if(strcmp(buffer,dest)!=0){
   	printf("FAILED: BUFFER IS %s\n",buffer);
   	printf("FAILED: DEST IS %s\n",dest);
   }
   else{
   printf("MARLO\n");
   
   }
   
   
  
   
   
  
   //int taco = open("write.txt", O_WRONLY | O_CREAT | O_TRUNC, 0644); 
   close(fd);
   
  
   return 0;
}
 
