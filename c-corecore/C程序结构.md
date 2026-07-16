
# 单文件的编排顺序

C语言对下述的顺序要求极少，更多的是出于对代码的良好规范。

1. `#include`指令。
2. `#define`指令。
3. 类型定义。
4. 外部变量定义（全局变量）。
5. 函数原型（函数签名）。
6. `main`函数入口。
7. 其他函数定义。


```c
/* #include directives go here */
/* #define directives go here  */
/* typedef  type defininations */
/* declarations of external variables go here */
/* function prototypes */

int main(void){
    /* body */
}

/* other function definations */

```