
# If语句



## 基于工时与累进税率的周薪计算程序


> 任务：编写一个 C 程序，计算你一周的薪资。

程序要求：

1. 通过键盘输入一周内工作的小时数。
2. 程序输出：
    1. 税前工资（毛工资，gross pay）
    2. 税金（taxes）
    3. 税后工资（净工资，net pay）

**假设条件**：

- 基本时薪 = $12.00/小时
- 超出 40 小时的部分为加班，按 1.5 倍时薪计算（即 $18/小时）
- 税率规则：
    - 收入中前**$300** 的部分，税率为 15%
    - 接下来的 **$150**（即 $300~$450 部分），税率为 20%
    - 超过 $450 的部分，税率为 25%

[exercise_01.c](./code/exercise_01.c)

```c
#include <stdio.h>

/* Constants */
#define BASE_PAY 12.0
#define OVERTIME_RATE 18.0 // 12 * 1.5
#define STANDARD_HOURS 40
#define TAX_BRACKET1 300
#define TAX_BRACKET2 450
#define TAXRATE_300 0.15
#define TAXRATE_450 0.20
#define TAXRATE_REST 0.25

int main(void)
{
    int hours;
    float gross_pay, taxes, net_pay;

    /* Get user input */
    printf("Please enter the number of hours worked in the week: ");
    scanf("%d", &hours);

    /* Calculate gross pay */
    if (hours <= STANDARD_HOURS)
    {
        gross_pay = hours * BASE_PAY;
    }
    else
    {
        gross_pay = STANDARD_HOURS * BASE_PAY + (hours - STANDARD_HOURS) * OVERTIME_RATE;
    }

    /* Calculate taxes using progressive tax brackets */
    if (gross_pay <= TAX_BRACKET1)
    {
        taxes = TAXRATE_300 * gross_pay;
    }
    else if (gross_pay <= TAX_BRACKET2)
    {
        taxes = TAXRATE_300 * TAX_BRACKET1 + (gross_pay - TAX_BRACKET1) * TAXRATE_450;
    }
    else
    {
        taxes = TAXRATE_300 * TAX_BRACKET1 + (TAX_BRACKET2 - TAX_BRACKET1) * TAXRATE_450 + (gross_pay - TAX_BRACKET2) * TAXRATE_REST;
    }

    /* Calculate net pay */
    net_pay = gross_pay - taxes;

    /* Display results */
    printf("\n========== Pay Summary ==========\n");
    printf("Gross pay: $%.2f\n", gross_pay);
    printf("Taxes:     $%.2f\n", taxes);
    printf("Net pay:   $%.2f\n", net_pay);
    printf("=================================\n");

    return 0;
}
```

Output
```sh
Please enter the number of hours worked in the week: 48

========== Pay Summary ==========
Gross pay: $624.00
Taxes:     $118.50
Net pay:   $505.50
=================================
```