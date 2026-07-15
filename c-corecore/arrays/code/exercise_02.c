#include <stdio.h>
#define BOARD_HEIGHT 10
#define BOARD_WIDTH 10

void print_arr(char arr[][BOARD_WIDTH]){
    for(int i=0; i<BOARD_HEIGHT; i++){
        for(int j=0;j<BOARD_WIDTH; j++){
            printf("%c ",arr[i][j]);
        }
        printf("\n");

    }
}

void init_arr(char arr[][BOARD_WIDTH],char init_val){
    for(int i=0; i<BOARD_HEIGHT; i++){
        for(int j=0;j<BOARD_WIDTH; j++){
            arr[i][j] = init_val;
        }
    }
}


int main(){
    char board[10][10];
    init_arr(board,'.');
    board[5][5] = 'A';
    print_arr(board);
    return 0;
}

