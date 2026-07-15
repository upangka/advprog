/*
 * exercise_02_refactor.c
 * 随机步法 - 重构版本
 *
 * 相比原始版本的改进：
 * 1. 单一职责原则：每个函数只做一件事
 * 2. 提取方向数组为全局常量，避免重复定义
 * 3. 提高可读性和可维护性
 * 4. 便于独立测试每个功能模块
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <stdbool.h>

#define BOARD_HEIGHT 10
#define BOARD_WIDTH 10

// 方向常量（全局，供所有函数使用）
const int DR[] = {-1, 1, 0, 0}; // 上下左右的行偏移
const int DC[] = {0, 0, -1, 1}; // 上下左右的列偏移
const int DIRECTION_COUNT = 4;  // 方向总数

// 打印数组
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

// 初始化数组
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

// 检查某个方向是否可走
bool is_valid_move(char board[][BOARD_WIDTH], int row, int col, int dir, char unvisited)
{
    int next_row = row + DR[dir];
    int next_col = col + DC[dir];

    return (next_row >= 0 && next_row < BOARD_HEIGHT &&
            next_col >= 0 && next_col < BOARD_WIDTH &&
            board[next_row][next_col] == unvisited);
}

// 收集所有可用方向，返回可用方向的数量
int find_available_directions(char board[][BOARD_WIDTH], int row, int col,
                              int available[], char unvisited)
{
    int count = 0;
    for (int dir = 0; dir < DIRECTION_COUNT; dir++)
    {
        if (is_valid_move(board, row, col, dir, unvisited))
        {
            available[count++] = dir;
        }
    }
    return count;
}

// 从可用方向中随机选择一个
int choose_random_direction(int available[], int count)
{
    int chosen_index = rand() % count;
    return available[chosen_index];
}

// 执行一步移动，返回是否成功
bool make_move(char board[][BOARD_WIDTH], int *row, int *col,
               char *letter, char unvisited)
{
    int available[DIRECTION_COUNT];
    int count = find_available_directions(board, *row, *col, available, unvisited);

    // 没有可用方向，游戏结束
    if (count == 0)
    {
        printf("At【%c】stop\n\n", *letter);
        return false;
    }

    // 随机选择一个方向并移动
    int dir = choose_random_direction(available, count);
    *row += DR[dir];
    *col += DC[dir];
    *letter += 1;
    board[*row][*col] = *letter;

    return true;
}

// 运行随机步法主逻辑
void run(char board[][BOARD_WIDTH], char unvisited)
{
    int row = 0, col = 0;
    char letter = 'A';
    board[row][col] = letter;

    // 只要还没到 Z 并且能继续移动，就循环
    while (letter < 'Z' && make_move(board, &row, &col, &letter, unvisited))
    {
        // 循环体为空，所有逻辑在 make_move 中
        // 这种写法让主循环非常简洁
        // 并且注意 row,col以及letter传递的都是地址
    }
}

int main()
{
    srand(time(NULL));
    char board[BOARD_HEIGHT][BOARD_WIDTH];

    init_arr(board, '.');
    run(board, '.');
    print_arr(board);

    return 0;
}
