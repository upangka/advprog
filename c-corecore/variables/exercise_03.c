#include <stdio.h>

int main(){

    enum suit {CLUBS,DIAMONDS=3,HEARTS=2,SPADES};
    enum suit s = SPADES;
    printf("%d",s);
}
