/*
七段显示器用7个段（0-6）来显示数字0-9：
 --0--
|     |
5     1
|     |
 --6--
|     |
4     2
|     |
 --3--
每个段要么亮（1），要么灭（0）。
 _      _   _       _   _   _   _   _
| |  |  _|  _| |_| |_  |_    | |_| |_|
|_|  | |_   _|   |  _| |_|   | |_|  _|
*/

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