#include <stdio.h>

void my_f(void){}

int main(){
    my_f();
    // X error: too many arguments to function ‘my_f’
    // my_f("hello","world");
    printf("Main over.\n");
}
