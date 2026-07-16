#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int roll_dice(void);
void play_game(void);

int main(void){

    int wins = 0,losses = 0;
    
    char continue;

    do{
        play_game();
        printf("Play again? ");
        scanf('%c',&continue);
    }while(continue == 'y' || continue == 'Y');


    

    printf("Wins: %d Losses: %d\n",wins,losses);
    exit(EXIT_SUCCESS);
}


void play_game(void){

}

