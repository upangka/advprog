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
