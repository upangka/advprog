from decimal import Decimal
from pathlib import Path

from exercise_01 import Holding

WIDTH = 15


class Portfolio:
    def __init__(self, holdings: list[Holding]):
        self._holdings = holdings

    def __iter__(self):  # 用于支持sorted
        return iter(self._holdings)


# sorted(Portfolio([]),key=lambda h: h.value)


def read_portfolio(file_name: str = "portfolio.csv") -> Portfolio:
    """
    Read a CSV file of name, shares, price data into a list of dicts.
    """
    holdings: list[Holding] = []

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
            holdings.append(
                Holding(name=name, shares=int(shares), price=Decimal(price))
            )

    return Portfolio(holdings)


def make_report(portfolio: Portfolio):
    """
    Print a report
    """
    print(" " + "-" * (WIDTH * 4 + 3))
    print(
        f"|{'name':^{WIDTH}}|{'shares':^{WIDTH}}|{'price':^{WIDTH}}|{'value':^{WIDTH}}|"
    )
    print("|" + ("-" * WIDTH + "|") * 4)

    total_value = 0
    for holding in sorted(portfolio, key=lambda h: h.value, reverse=True):
        value = holding.value
        total_value += value
        print(
            f"|{holding.name:^{WIDTH}s}|{holding.shares:^{WIDTH}d}|{holding.price:^{WIDTH}.2f}|{value:^{WIDTH}.2f}|"
        )
    print(" " + "-" * (WIDTH * 4 + 3))
    print(f"\nTotal value: {total_value:.2f}")


def main():
    port = read_portfolio()
    make_report(port)


if __name__ == "__main__":
    main()
