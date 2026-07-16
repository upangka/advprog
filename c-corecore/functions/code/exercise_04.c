#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

int roll_dice(void);
bool play_game(void);

int main(void)
{
    int wins = 0, losses = 0;
    char play_again = 'y';

    // 初始化随机数种子
    srand((unsigned)time(NULL));

    do
    {
        if (play_game())
        {
            wins++;
            printf("You win!\n");
        }
        else
        {
            losses++;
            printf("You losses\n");
        }
        printf("\nPlay again? ");
        scanf(" %c", &play_again);
        printf("\n");
    } while (play_again == 'y' || play_again == 'Y');

    printf("Wins: %d Losses: %d\n", wins, losses);
    exit(EXIT_SUCCESS);
}

/*
 * play_game: 执行一局完整的掷骰子游戏
 * 返回值: true 表示玩家获胜，false 表示玩家落败
 */
bool play_game(void)
{
    //  -1 表示尚未设置目标点数
    int target = -1, dice = 0;

    while (true)
    {

        dice = roll_dice();
        printf("You rolled: %d\n", dice);

        // 第一次投掷
        if (target == -1)
        {
            // 判断输赢
            if (dice == 7 || dice == 11)
            {
                return true;
            }
            else if (dice == 2 || dice == 3 || dice == 12)
            {
                return false;
            }
            else
            {
                target = dice;
                printf("Your point is: %d\n", target);
            }
            continue;
        }

        // 判断其他投掷输赢
        if (dice == target)
        {
            return true;
        }
        else if (dice == 7)
        {
            return false;
        }
    }
}

/*
 * roll_dice: 掷两个骰子
 * 返回值: 两个骰子点数之和 (2 ~ 12)
 */
int roll_dice(void)
{
    int dice1 = rand() % 6 + 1;
    int dice2 = rand() % 6 + 1;
    return dice1 + dice2;
}
