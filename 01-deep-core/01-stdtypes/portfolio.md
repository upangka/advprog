
# Portfolio Project Introduction

This file [portfolio.py](./code/portfolio/portfolio.py) contains a function that reads a CSV file of
"name,shares,price" data into a list of dictionaries. The file
[report.py](./code/portfolio/report.py) uses this function. We'll make some modifications
in exercises below.

## portfolio.csv

[portfolio.csv](./code/portfolio/portfolio.csv)

![](./images/portfolio.png)

## portfolio.py

[portfolio.py](./code/portfolio/portfolio.py)

```python
from pathlib import Path
from typing import Any


def read_portfolio(file_name: str = "portfolio.csv") -> list[dict[str, Any]]:
    """
    Read a CSV file of name, shares, price data into a list of dicts.
    """
    portfolio = []

    # python交换环境中没有__file__
    if "__file__" in globals():
        file_path = Path(__file__).parent / file_name
    else:
        file_path = Path(file_name)

    with open(file_path, encoding="utf-8") as f:
        # 忽略第一行头部信息, isinstance(f,Iterable) -> True
        next(f)
        for line in f:
            name, shares, price = (item.strip() for item in line.split(","))
            portfolio.append(dict(name=name, shares=shares, price=price))

    return portfolio


if __name__ == "__main__":
    results = read_portfolio()
    [print(r) for r in results]
```
输出:
```python
{'name': 'AA', 'shares': '100', 'price': '32.20'}
{'name': 'IBM', 'shares': '50', 'price': '91.10'}
{'name': 'CAT', 'shares': '150', 'price': '83.44'}
{'name': 'MSFT', 'shares': '200', 'price': '51.23'}
{'name': 'GE', 'shares': '95', 'price': '40.37'}
{'name': 'ACME', 'shares': '50', 'price': '65.10'}
{'name': 'YOW', 'shares': '100', 'price': '70.44'}
```

---

## report.py

Ben has decided to write some Python code to manage his stock
portfolio. The file "portfolio.csv" is a CSV file containing some
information about his stock holdings (name, number of shares,
price). The following program reads this file, sorts it, and prints
out a small report.

The module `portfolio.py` contains code for reading the data
and returning it back as a list of dictionaries. Take a few
moments to run the program and look at the code.

Upon showing this program to his co-workers, Ben is immediately
accosted by his decision to directly use Python data structures
such as dicts and lists. "You should really use some classes
or something man" noted Peter.

- accosted /əˈkɑːstɪd/ v. 被搭话；被贸然上前交谈（指被某人突然接近并主动搭话，通常带有一种出其不意、甚至略带挑衅或对抗的意味。在上下文中，“Ben is immediately accosted by his decision to directly use Python data structures such as dicts and lists.” 的意思是：Ben 因为直接使用 Python 数据结构（如字典和列表）而被同事上前质问/理论。

Your task in this project is to explore this central question:
**Should you use custom classes to provide a kind of data abstraction
layer or is it perfectly fine to use dictionaries and lists?**
If classes are used, what should they look like?

Most of your work will take place in the `portfolio.py` file. You
will make a few minor code modifications here, but the code in
this file should keep its original organization (i.e., you'll keep
the `make_report()` and `main()` functions).

[report.py](./code/portfolio/report.py)

```python
import portfolio

WIDTH = 15

def make_report(portfolio: list):
    """
    Print a report
    """
    portfolio.sort(key=lambda h: h["shares"] * h["price"], reverse=True)
    print(" " + "-" * (WIDTH * 4 + 3))
    print(
        f"|{'name':^{WIDTH}}|{'shares':^{WIDTH}}|{'price':^{WIDTH}}|{'value':^{WIDTH}}|"
    )
    print("|" + ("-" * WIDTH + "|") * 4)

    total_value = 0
    for holding in portfolio:
        value = holding["shares"] * holding["price"]
        total_value += value
        name, shares, price = holding["name"], holding["shares"], holding["price"]
        print(
            f"|{name:^{WIDTH}s}|{shares:^{WIDTH}d}|{price:^{WIDTH}.2f}|{value:^{WIDTH}.2f}|"
        )
    print(" " + "-" * (WIDTH * 4 + 3))
    print(f"\nTotal value: {total_value:.2f}")


def main():
    port = portfolio.read_portfolio()
    make_report(port)


if __name__ == "__main__":
    main()
```

输出
```sh
 ---------------------------------------------------------------
|     name      |    shares     |     price     |     value     |
|---------------|---------------|---------------|---------------|
|      CAT      |      150      |     83.44     |   12516.00    |
|     MSFT      |      200      |     51.23     |   10246.00    |
|      YOW      |      100      |     70.44     |    7044.00    |
|      IBM      |      50       |     91.10     |    4555.00    |
|      GE       |      95       |     40.37     |    3835.15    |
|     ACME      |      50       |     65.10     |    3255.00    |
|      AA       |      100      |     32.20     |    3220.00    |
 ---------------------------------------------------------------

Total value: 44671.15
```