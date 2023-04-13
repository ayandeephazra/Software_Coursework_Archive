////////////////////////////////////////////////////////////////////////////////
//
// Author:           Ayan Deep Hazra
// Email:            ahazra2@wisc.edu
// CS Login:         ayan
//
//////////////////////////// 80 columns wide ///////////////////////////////////

#define ROWS 3000
#define COLUMNS 500

int arr2D[ROWS][COLUMNS];

int main(){

	int row;
	int col;


	for(row = 0; row < ROWS; row++){
		for(col = 0; col < COLUMNS; col++){
			arr2D[row][col] = row + col;
		}

	}

	return 0;
}
