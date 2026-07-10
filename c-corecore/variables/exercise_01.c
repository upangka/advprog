#include <stdio.h>

int main()
{
    int a; // （定义变量时）：此时内存就已经分配了！ 编译器在编译阶段就决定好了。
    printf("           address: %p\n", &a);
    a = 3; //  （赋值时）：此时内存不再分配！ 只是在已有的盒子里写入数据 3。
    printf("a = 3 时的 address: %p\n", &a);
    a = 5;
    printf("a = 5 时的 address: %p\n", &a);
    return 0;
}
