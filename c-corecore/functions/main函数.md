
`main`是整个程序的入口点，是操作系统加载你的程序后，第一个调用的函数。


# 退出形式

1. `main`函数中执行`return`背后会调用`exit`.
2. `EXIT_SUCCESS`和`EXIT_FAILURE`都是定义在`<stdlib.h>`中的宏。 

[exercise_03.c](./code/exercise_03.c)

```c
/*
 exit函数和变量EXIT_SUCCESS来自stdlib.h
 */
#include <stdio.h>
#include <stdlib.h>

void cleanup()
{
    printf("在退出之前执行的清理操作");
}

int main(void)
{
    atexit(cleanup);
    printf("RUNNING ...\n");

    // exit(EXIT_SUCCESS);
    return 0;
}
```

输出:

```sh
RUNNING ...
在退出之前执行的清理操作
```