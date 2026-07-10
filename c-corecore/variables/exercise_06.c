/*
 * 输入长方形的width 和 height计算area面积和周长perimeter 
 */
#include <stdio.h>

int main()
{
    double area = 0.0,perimeter;
    double width,height;
    
    printf("Please input width and height seperate by blank\n");
    scanf("%lf %lf",&width,&height);

    perimeter = 2 * (width + height);
    area = width * height;

    printf("perimeter = %g\n",perimeter);
    printf("area = %g\n",area);
    printf("width = %g\n",width);
    printf("height = %g",height);
}
