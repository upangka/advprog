#include <stdio.h>
#include <stdbool.h>

int main()
{
    // 整数
    // short int
    short a;
    int b;
    // long int
    long c;
    scanf("%hd %d %ld", &a, &b, &c);
    printf("a = %hd | b = %d | c = %ld\n", a, b, c);
    // 无符号数
    unsigned short us;
    unsigned int ui;
    unsigned long ul;

    // unsign int -> u,对应的short->hu,long -> lu
    scanf("%hu %u %lu", &us, &ui, &ul);
    printf("us = %hu | ui = %u | ul = %lu\n", us, ui, ul);

    // 单精度浮点数
    float f1;
    // 双精度浮点数
    double f2;
    // 扩展精度浮点数
    long double f3;

    scanf("%f %lf %Lf", &f1, &f2, &f3);
    printf("f1 = %f | f2 = %f | f3 = %Lf\n", f1, f2, f3);
    printf("f1 = %g | f2 = %g | f3 = %Le\n", f1, f2, f3);

    char d = 65; // 'A' ascii
    char e = 'B';
    char g = d + 1;
    printf("d = %c | e = %c | g = %c\n", d, e, g);

    // 转义字符
    char z = '\n';
    printf("%c", z);

    // C99 <stdbool.h> 引入的bool
    bool h = g == e;
    // 本质是整数1或0
    printf("g == e ? %d\n", h);

    return 0;
}
/*output:
-20 -100 -300
a = -20 | b = -100 | c = -300
20 100 300
us = 20 | ui = 100 | ul = 300
3.14 3.14159265 3.141592653
f1 = 3.140000 | f2 = 3.141593 | f3 = 3.141593
f1 = 3.14 | f2 = 3.14159 | f3 = 3.141593e+00
d = A | e = B | g = B

g == e ? 1
*/