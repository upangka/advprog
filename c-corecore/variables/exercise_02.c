#include <stdio.h>

int main()
{
    int a = 3;
    // 取值
    printf("%d\n", a);

    // 内存地址
    printf("%p\n", &a);

    // 定义指针，相当于Java/Python的引用一样
    int *p = &a;
    printf("%p\n", p);
    // 通过地址访问
    printf("%d\n", *p);

    // 因为指向同一块内存地址，修改p也是修改a
    *p = 5;
    printf("%d\n", a);

    return 0;
}

// 3
// 0x7ffe6bf0885c
// 0x7ffe6bf0885c
// 3
// 5