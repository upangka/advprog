/*
编写程序，生成一种贯穿10×10字符数组（初始时全为字符'.'）的“随机步法”​。程序必须随机地从一个元素“走到”另一个元素，每次都向上、向下、向左或向右移动一个元素位置。已访问过的元素按访问顺序用字母A到Z进行标记。

 */
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
    

    print_arr(board);
    return 0;
}

