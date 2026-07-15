/*
编写程序，生成一种贯穿10×10字符数组（初始时全为字符'.'）的“随机步法”​。程序必须随机地从一个元素“走到”另一个元素，每次都向上、向下、向左或向右移动一个元素位置。已访问过的元素按访问顺序用字母A到Z进行标记。

 */
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>

#define BOARD_HEIGHT 10
#define BOARD_WIDTH 10
#define LETTERS_SIZE 26

void print_arr(char arr[][BOARD_WIDTH])
{
    for (int i = 0; i < BOARD_HEIGHT; i++)
    {
        printf("\t");
        for (int j = 0; j < BOARD_WIDTH; j++)
        {
            printf("%c ", arr[i][j]);
        }
        printf("\n");
    }
}

void init_arr(char arr[][BOARD_WIDTH], char init_val)
{
    for (int i = 0; i < BOARD_HEIGHT; i++)
    {
        for (int j = 0; j < BOARD_WIDTH; j++)
        {
            arr[i][j] = init_val;
        }
    }
}

void run(char board[][BOARD_WIDTH], char unvisited)
{
    // 初始位置
    int row = 0, col = 0;
    char letter = 'A';
    board[row][col] = letter;

    // dc,dy同行数组组成上下左右的方向
    int dr[] = {-1, 1, 0, 0};
    int dc[] = {0, 0, -1, 1};

    while (letter < 'Z')
    {
        // 用数组保存所有可移动的方向，随机取一个
        int available[4];
        int count = 0;

        // find next direction
        for (int i = 0; i < 4; i++)
        {
            int next_row = row + dr[i];
            int next_col = col + dc[i];

            // 边界与占位判断
            if (next_row >= 0 && next_row < BOARD_HEIGHT &&
                next_col >= 0 && next_col < BOARD_WIDTH &&
                board[next_row][next_col] == unvisited)
            {
                available[count] = i;
                count++;
            }
        }

        if (count == 0)
        {
            // Game Over
            printf("At【%c】stop\n\n", letter);
            break;
        }

        // 在可用列表中随机获取一个可用的方向
        int chosen = rand() % count;
        int d = available[chosen];

        row += dr[d];
        col += dc[d];

        // update
        letter += 1;
        board[row][col] = letter;
    }
}

int main()
{
    srand(time(NULL));
    char board[10][10];
    init_arr(board, '.');
    run(board, '.');
    print_arr(board);
    return 0;
}
