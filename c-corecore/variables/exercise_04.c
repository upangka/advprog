#include <stdio.h>

int main()
{
    // 定义枚举
    typedef enum
    {
        BeiJing,      // 默认0开始
        ShenZhen,     // 值比前一个大一
        ShangHai = 5, // 可以自定义整数值
        GuangZhou,    // 值比前一个大一
        GuiLin = 5    // 允许重复，因为C语言中，枚举本质是整数常量
    } City;

    // 声明变量,枚举直接在当前作用域，直接获取赋值
    City bj = BeiJing;
    City sz = ShenZhen;
    City sh = ShangHai;
    City gz = GuangZhou;
    City gl = GuiLin;

    printf("bj is %d\n", bj);
    printf("sz is %d\n", sz);
    printf("sh is %d\n", sh);
    printf("gz is %d\n", gz);
    printf("gl is %d\n", gl);
}

/*output:
bj is 0
sz is 1
sh is 5
gz is 6
gl is 5
*/