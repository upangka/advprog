需要两个标准库

1. `stdlib.h`提供`srand`(设置随机生成器)和`rand`(生成随机数)。
2. `time.h`使用当前时间作为随机数种子。

[exercise_01.c](./code/exercise_01.c)

```c
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main()
{

    time_t t;
    // 复制给t并返回一模一样的值
    printf("%ld\n", time(&t));
    printf("%ld\n", t);

    // srand(time(&t));
    srand(t);
    for (int i = 0; i < 5; i++)
    {
        printf("%d\n", rand() % 21);
    }

    return 0;
}
/*output
1784044120  时间戳一样
1784044120
 产生的随机数
15
14
20
20
18
*/
```