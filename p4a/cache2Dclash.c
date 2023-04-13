////////////////////////////////////////////////////////////////////////////////
//
// Author:           Ayan Deep Hazra
// Email:            ahazra2@wisc.edu
// CS Login:         ayan
//
//////////////////////////// 80 columns wide ///////////////////////////////////

#define ROWS 128
#define COLUMNS 8

int arr2D[ROWS][COLUMNS];

int main(){
	int row;
	int col;
	int iteration;
	for (iteration=0; iteration<100; iteration++){
		for (row=0; row<ROWS; row=row+64){
			for (col=0; col<COLUMNS; col++){
				arr2D[row][col] = iteration + row + col;
			}
		}
	}
	return 0;
}

