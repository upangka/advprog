from decimal import Decimal
from pathlib import Path

from exercise_01 import Holding

WIDTH = 15

from typing import Protocol,Iterator

class SupportPortfolio(Protocol):
    def __iter__(self) -> Iterator[Holding]: ...
    
    def total_value(self) -> Decimal: ...
    

class Portfolio:
    def __init__(self, holdings: list[Holding]):
        self._holdings = holdings

    def __iter__(self):  # 用于支持sorted
        return iter(self._holdings)

    # @property
    def total_value(self):
        "change from property to method"
        return sum((h.shares * h.price for h in self),Decimal('0'))


def read_portfolio(file_name: str = "portfolio.csv") -> Portfolio:
    """
    Read a CSV file of name, shares, price data into a list of dicts.
    """
    holdings: list[Holding] = []

    # python交互环境中没有__file__
    if "__file__" in globals():
        file_path = Path(__file__).parent / file_name
    else:
        file_path = Path(file_name)

    with open(file_path, encoding="utf-8") as f:
        # 忽略第一行头部信息, isinstance(f,Iterable) -> True
        next(f)
        for line in f:
            name, shares, price = (item.strip() for item in line.split(","))
            holdings.append(
                Holding(name=name, shares=int(shares), price=Decimal(price))
            )

    return Portfolio(holdings)


def make_report(portfolio: SupportPortfolio):
    """
    Print a report
    """
    print(" " + "-" * (WIDTH * 4 + 3))
    print(
        f"|{'name':^{WIDTH}}|{'shares':^{WIDTH}}|{'price':^{WIDTH}}|{'value':^{WIDTH}}|"
    )
    print("|" + ("-" * WIDTH + "|") * 4)

    for holding in sorted(portfolio, key=lambda h: h.value, reverse=True):
        value = holding.value
        print(
            f"|{holding.name:^{WIDTH}s}|{holding.shares:^{WIDTH}d}|{holding.price:^{WIDTH}.2f}|{value:^{WIDTH}.2f}|"
        )
    print(" " + "-" * (WIDTH * 4 + 3))
    print(f"\nTotal value: {portfolio.total_value():.2f}")


def main():
    port = read_portfolio()
    make_report(port)


if __name__ == "__main__":
    main()
