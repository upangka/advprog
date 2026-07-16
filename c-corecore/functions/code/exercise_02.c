#include <stdio.h>

int tst_f(int n, int arr[n]);

int main()
{

    int arr[100] = {1, 2, 3};
    printf("arr大小: %zu\n", sizeof(arr));

    tst_f(3, arr);
}

int tst_f(int n, int arr[n])
{
    printf("arr大小: %zu\n", sizeof(arr));
}
