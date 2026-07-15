
# 一维数组

**初始化器**

```c
int a[10] = {1,2,3,4,5,6,7,8,9,10};

// 初始化器比数组短，剩余元素赋值0
int a[10] = {1,2,3,4,5,6};
/* initial value of a is {1,2,3,4,5,6,0,0,0,0} */

// 定义初始化器可以省略数组长度
int a[] = {1,2,3,4,5,6,7,8,9,10};

```

**C99指示器**

```c
int a[15] = {[14] = 48,[9] = 7,[2] = 29};

// 二维数组
int a[2][2] = {[0][0] = 1.0,[1][1] = 1.0};
```

**常量数组**

```c
// 方向常量（全局，供所有函数使用）
const int DR[] = {-1, 1, 0, 0}; // 上下左右的行偏移
const int DC[] = {0, 0, -1, 1}; // 上下左右的列偏移
```

## 随机步法（Random Walk）

# 随机步法（Random Walk）

## 题目描述

编写程序，生成一种贯穿 `10 x 10` 字符数组（初始时全为字符 `'.'`）的**随机步法**。

**核心规则**： 

程序必须随机地从一个元素**走**到另一个元素，每次都向上、向下、向左或向右移动**一个**元素位置。已访问过的元素按访问顺序用字母 `A` 到 `Z` 进行标记。

**输出示例**

```
A  .  .  .  .  .  .  .  .  . 
B  C  D  .  .  .  .  .  .  . 
.  F  E  .  .  .  .  .  .  . 
H  G  .  .  .  .  .  .  .  . 
I  .  .  .  .  .  .  .  .  . 
J  .  .  .  .  .  .  .  .  . 
K  .  .  R  S  T  U  V  W  X 
L  M  P  Q  .  .  .  .  .  Y 
.  N  O  .  .  .  .  .  .  Z 
.  .  .  .  .  .  .  .  .  .
```

实现提示 **随机数生成**
- 利用 `srand` 函数和 `rand` 函数产生随机数
- 查看此数除以 `4` 的余数，余数共有 `4` 种可能值（`0`、`1`、`2`、`3`），分别指示下一次移动的 `4` 种可能方向

**移动前检查**: 在执行移动之前，需要检查两项内容：
1. **不能走到数组外面**（边界检查）
2. **不能走到已有字母标记的位置**（访问检查）

只要有一个条件不满足，就得**尝试换一个方向**移动。

**终止条件**:  如果 `4` 个方向都堵住了，程序就必须终止。

提前结束示例

```
A  B  G  H  I  .  .  .  .  .
.  C  F  .  J  K  .  .  .  .
.  D  E  .  M  L  .  .  .  .
.  .  .  .  N  O  .  .  .  .
.  .  .  W  X  Y  P  Q  .  .
.  .  .  V  U  T  S  R  .  .
.  .  .  .  .  .  .  .  .  .
.  .  .  .  .  .  .  .  .  .
.  .  .  .  .  .  .  .  .  .
.  .  .  .  .  .  .  .  .  .
```

因为 `Y` 的 `4` 个方向都堵住了，所以没有地方可以放置下一步的 `Z` 了。

为了节省篇幅，这里只给出优化版本[exercise_02_refactor.c](./code/exercise_02_refactor.c)使用了clean code的优化原则，抽离了函数。最原始的版本见[exercise_02.c](./code/exercise_02.c)

```c
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

```



运行输出，这是最后没有可移动方向的一种情况

```sh
At【X】stop

        A . . . . . . . . .
        B . . . . . . . . .
        C . . . . . . . . .
        D E . . . . . . . .
        . F G J K L . . . .
        . . H I . M N O . .
        . . . . . . . P Q R
        . . . . . . . . T S
        . . . . . . . . U X
        . . . . . . . . V W
```



# 二维数组


二维数组定义时必须指定列数（第二维的大小），或者完整初始化。

```c
const segments[][7] = {
    {1,1,1,1,1,1,0}
}
```


## 七段显示器

```sh
七段显示器用7个段（0-6）来显示数字0-9：
  0
  _
5|_|1  中间为6
4|_|2
  3
每个段要么亮（1），要么灭（0）。
 _      _   _       _   _   _   _   _
| |  |  _|  _| |_| |_  |_    | |_| |_|
|_|  | |_   _|   |  _| |_|   | |_|  _|
```

[exercise_01.c](./code/exercise_01.c)

```c
#include <stdio.h>

/* 编码表.
   初始化器进行初始化
   一维数组下标代表数字，二维下标代表明亮
*/
// const int segments[10][7] 可以省略前面但是第二维度不能省略
const int segments[][7] = {
    {1, 1, 1, 1, 1, 1, 0},
    {0, 1, 1, 0, 0, 0, 0},
    {1, 1, 0, 1, 1, 0, 1},
    {1, 1, 1, 1, 0, 0, 1},
    {0, 1, 1, 0, 0, 1, 1},
    {1, 0, 1, 1, 0, 1, 1},
    {1, 0, 1, 1, 1, 1, 1},
    {1, 1, 1, 0, 0, 0, 0},
    {1, 1, 1, 1, 1, 1, 1},
    {1, 1, 1, 1, 0, 1, 1}};

void show_segment(int digit)
{

    if (digit < 0 || digit > 9)
    {
        printf("Invalid digit\n");
        return;
    }

    // 第1行：上横
    printf(" ");
    printf("%c", segments[digit][0] ? '_' : ' ');
    printf(" \n");

    // 第2行：左上竖 + 中横 + 右上竖
    printf("%c", segments[digit][5] ? '|' : ' ');
    printf("%c", segments[digit][6] ? '_' : ' ');
    printf("%c\n", segments[digit][1] ? '|' : ' ');

    // 第3行：左下竖 + 下横 + 右下竖
    printf("%c", segments[digit][4] ? '|' : ' ');
    printf("%c", segments[digit][3] ? '_' : ' ');
    printf("%c\n", segments[digit][2] ? '|' : ' ');
}

int main()
{
    for (int i = 0; i < 10; i++)
    {
        show_segment(i);
    }
    return 0;
}
```