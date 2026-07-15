#include <stdio.h>
#define YEARS 5
#define MONTHS 12
#define YEARBASE 2026

int main()
{
    const float RAIN[YEARS][MONTHS] = {
        {4.3, 4.3, 4.3, 3.0, 2.0, 1.2, 0.2, 0.2, 0.4, 2.4, 3.5, 6.6}, // 第1年
        {8.5, 8.2, 1.2, 1.6, 2.4, 0.0, 5.2, 0.9, 0.3, 0.9, 1.4, 7.3}, // 第2年
        {9.1, 8.5, 6.7, 4.3, 2.1, 0.8, 0.2, 0.2, 1.1, 2.3, 6.1, 8.4}, // 第3年
        {7.2, 9.9, 8.4, 3.3, 1.2, 0.8, 0.4, 0.0, 0.6, 1.7, 4.3, 6.2}, // 第4年
        {7.6, 5.6, 3.8, 2.8, 3.8, 0.2, 0.0, 0.0, 0.0, 1.3, 2.6, 5.2}  // 第5年
    };

    int year, month;
    double subtot = 0.0, total = 0.0;

    // print year
    printf("YEAR\t\tRAINFALL (inches)\n");
    for (total = 0.0, year = 0; year < 5; year++)
    {
        subtot = 0.0;
        for (subtot = 0.0, month = 0; month < 12; month++)
        {
            subtot += RAIN[year][month];
        }
        printf("%4d\t\t%-6.1f\n", year + YEARBASE, subtot);
        total += subtot;
    }
    printf("\nThe yearly average is %.2f inches.\n", total / 5);
    printf("\n");

    // 每个月近5年的平均数
    printf("%-5s %-5s %-5s %-5s %-5s %-5s %-5s %-5s %-5s %-5s %-5s %-5s\n",
           "Jan", "Feb", "Mar", "Apr", "May", "Jun",
           "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

    for (month = 0; month < 12; month++)
    {

        for (subtot = 0.0, year = 0; year < 5; year++)
        {
            subtot += RAIN[year][month];
        }

        printf("%-5.1f ", subtot / 5);
    }
    return 0;
}
/*
YEAR            RAINFALL (inches)
2026            32.4
2027            37.9
2028            49.8
2029            44.0
2030            32.9

The yearly average is 39.40 inches.

Jan   Feb   Mar   Apr   May   Jun   Jul   Aug   Sep   Oct   Nov   Dec
7.3   7.3   4.9   3.0   2.3   0.6   1.2   0.3   0.5   1.7   3.6   6.7
*/
