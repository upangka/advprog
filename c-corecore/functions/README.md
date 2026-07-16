# 函数的定义

无参数的形式`void`

下面的这种无参形式，居然可以在调用的时候传递参数。能够正常编译和运行
```c
void my_f(){}

int main(){
    my_f();
    my_f("hello","world");
}
```

生态中标准的写法,当没有接受参数的时候明确表明是`void`

```c
void my_f(void){}

int main(){
    my_f();
    // X error: too many arguments to function ‘my_f’
    // my_f("hello","world");
}
```

# 函数声明（函数原型或函数签名）

**解决问题**：为了避免调用函数在定义函数之前。因为编译器调用函数的时候，需要知道函数有多少形式参数，形式参数的类型是什么，以及返回值是什么类型。

注意：并不关心函数的形参名称。

```c
double average(double,double);
```

## 变长数组形式参数

`int a[n]` 在形参中根本不是数组，它是**指针**！

```c
int sum_array(int n, int a[n]) { ... }
// 编译器实际看到的代码是：
int sum_array(int n, int *a) { ... }
```

`int a[n]` 在形参中不是创建数组，而是告诉编译器：`"嘿，我期望传入一个指针，这个指针指向至少 n 个 int 元素"`它**不分配内存**，**不检查长度**，只是一个文档注释！

**作用**：
1. 文档化：告诉阅读代码的人"这个指针应该指向至少`n`个元素的数组"
2. 静态分析：某些工具（如 `Clang`）可以利用这个信息检测越界访问


**C 语言中，只有"变量定义"才会分配内存，"形参声明"不分配内存。**

| 声明 | 是否分配内存 | 分配在哪里 | 分配多少 |
| :--- | :--- | :--- | :--- |
| `int a[100];`（局部变量） | ✅ 分配 | 栈（stack） | `100 * sizeof(int)` = 400 字节（64位系统） |
| `void f(int a[100]) {}`（函数形参） | ❌ 不分配 | 不分配任何数组内存 | 形参 `a` 本身是一个指针变量，在栈上占 8 字节（64位系统），但**不分配** 400 字节的数组内存 |

使用变长数组形式参数时，顺序很重要，长度在数组之前.

```c
int sum_array(int n,int arr[n]);
int sum_array(int n,int arr[*]);
int sum_array(int,int arr[*]);
```
`*` 表示一个占位符，取代数组长度。尽管函数声明时形式参数名称是可以省略的，但是如果不是像上面那样简单的参数，建议直接声明形式参数名称，表明关系。

```c
int concatenate(int m,int n,int a[m],int b[n],int c[m+n]);
```

### 形参数组是指针的实验验证

```c
#include <stdio.h>

int tst_f(int n, int arr[n]);

int main()
{

    int arr[100] = {1, 2, 3};
    printf("arr大小: %zu\n", sizeof(arr));

    tst_f(3, arr);
}

int tst_f(int n, int arr[n])
{
    printf("arr大小: %zu\n", sizeof(arr));
}
```

`gcc`编译警告`warning`:

```sh
warning: ‘sizeof’ on array function parameter ‘arr’ will return size of ‘int *’ [-Wsizeof-array-argument]
```

"你在 tst_f 函数里对 arr 用 sizeof，但 arr 是形参，它实际上是指针。所以 sizeof(arr) 返回 8，不是你想的 400。我警告你一下，免得你搞错。"

实际输出：

```c
arr大小: 400
arr大小: 8
```

很明显形参中数组的外壳，实际上已经是一个指针。这里指针占据8字节。

> **补充**: 在64位系统上占8字节，在32位系统上占4字节





## static修饰数组形参

`static 3` 告诉编译器：调用者传入的数组 `a`必须至少有`3`个元素。

```c
int sum_array(int a[static 3], int n) {
    // ...
}
```

`static`的存在只不过是一个"提示"，C编译器可以据此生成更快的指令来访问数组。（如果编译器知道数组总是具有某个最小值，那么它可以在函数调用时预先从内存中取出这些元素值，而不是在遇到函数内部需要用到这些元素的语句时才取出响应的值）

# 练习题

## 掷骰子游戏（Craps）

模拟掷骰子游戏（两个骰子），规则如下：
1. **第一次投掷（首轮）**：
   - 如果点数之和为 **7** 或 **11** → 玩家**获胜**
   - 如果点数之和为 **2**、**3** 或 **12** → 玩家**落败**
   - 其他情况（4、5、6、8、9、10） → 该点数成为玩家的 **“目标点数”**，游戏进入下一阶段

2. **后续投掷**：
   - 如果玩家再次掷出 **“目标点数”** → 玩家**获胜**
   - 如果玩家掷出 **7** → 玩家**落败**
   - 其他点数 → **忽略**，继续投掷

| 函数原型 | 功能描述 |
| :--- | :--- |
| `int roll_dice(void);` | 模拟掷两个骰子，每个骰子 1~6，返回点数之和（2~12） |
| `bool play_game(void);` | 执行一局完整的掷骰子游戏，如果玩家获胜返回 `true`，落败返回 `false`，并在过程中打印每次掷骰的结果 |
| `int main(void);` | 反复调用 `play_game()`，记录获胜/落败的次数，显示结果，询问用户是否继续，最终输出总胜败数 |

示例：

```sh
You rolled: 8
Your point is 8
You rolled: 3
You rolled: 10
You rolled: 8
You win!

Play again? y

You rolled: 6
Your point is 6
You rolled: 5
You rolled: 12
You rolled: 3
You rolled: 7
You lose!

Play again? y

You rolled: 11
You win!

Play again? n

Wins: 2 Losses: 1
```


[exercise_04.c](./code/exercise_04.c)

```c
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
```


### 补充

你的输入过程：
1. 你输入 `y` 然后按回车
2. 缓冲区中的内容：`y` `\n`
3. `scanf("%c")` 读取第一个字符 → `y`（赋值给 `play_again`）
4. 循环继续，再次执行 `scanf("%c")`
5. 这次读取缓冲区中剩下的 `\n` → 赋值给 `play_again`
6. `play_again == \n`，不满足循环条件，退出

输入缓冲区里残留了上一次输入的回车符 `\n`，`scanf("%c")` 读到这个 `\n` 后，直接把它当作 `play_again` 的值

最直接的解决方式：在 `%c` 前加空格

```c
do{
    scanf(" %c", &play_again);  // 前面的空格会跳过所有空白字符
//     ↑
//   注意这个空格！
}while(play_again == 'y' || play_again == 'Y');
```

原理：格式字符串中的空格告诉 `scanf`："跳过所有空白字符（空格、换行、制表符），然后读取第一个非空白字符"。

