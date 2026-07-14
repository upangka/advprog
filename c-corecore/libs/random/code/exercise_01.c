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
1784044120
1784044120
15
14
20
20
18
*/
