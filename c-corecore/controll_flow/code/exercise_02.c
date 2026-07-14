/*
 * Guessing Game
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define LOW 0
#define HIGH 20
#define TRIES 5

void run_game(int ans)
{
    int guess;
    for (int i = TRIES; i > 0; i--)
    {
        printf("You have %d tries left.\n", i);
        printf("Enter a guess: ");
        scanf("%d", &guess);

        // judge
        if (guess > ans)
        {
            printf("Sorry,%d is wrong. My number is less than that.\n", guess);
        }
        else if (guess < ans)
        {
            printf("Sorry,%d is wrong. My number is greater than that.\n", guess);
        }
        else
        {
            printf("Congratulations. You guessed it!\n");
            break;
        }
    }
}

int main()
{

    printf("This is a guessing game.\n");
    printf("I have chosen a number between %d and %d which you must guess.\n\n", LOW, HIGH);

    // set random generator
    time_t t;
    srand(time(&t));
    int answer = rand() % (HIGH + 1);
    printf("-----------cheat (%d)---------------\n", answer);

    run_game(answer);

    return 0;
}
