#include <string.h>
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

void pzip(char *str){

int length=strlen(str);

for(int i=0;i<length;i++){
int count=1;
while(i<length-1 && str[i]==str[i+1]){
count++;
i++;
}
if(str[i]!='\n'){
//printf("%d%c",count,str[i]);
fwrite(&count,4,1,stdout);
fwrite(&str[i],1,1,stdout);
}
else{
//printf("%d\\n",count);
char nextLine='\n';
fwrite(&count,4,1,stdout);
fwrite(&nextLine,1,1,stdout);
}
}
//printf("\n");
}

int main(int argc, char *argv[]){
FILE *fpointer =fopen(argv[1],"r+");

while(1){
char *line=NULL;
int length;
size_t len=0;
length=getline(&line, &len, fpointer);
if(length==-1){
exit(0);
}
  pzip(line);
  }
  fclose(fpointer);
 
return 0;
}