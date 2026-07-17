#include <stdio.h>
#include <string.h>

int main(void){
    
    char str1[] = "hello";
    char str2[] = "world";
    char *p = strcat(str1,str2);
    // %s,需要的是一个地址
    printf("str1 = %s\n",p);
    printf("str1 = %s\n",str1);
    return 0;
}
