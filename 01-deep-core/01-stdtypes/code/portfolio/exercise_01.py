from dataclasses import dataclass
from decimal import Decimal
from pathlib import Path

WIDTH = 15


@dataclass
class Holding:
    name: str
    shares: int
    price: Decimal

    def value(self):
        return self.shares * self.price


def read_portfolio(file_name: str = "portfolio.csv") -> list[Holding]:
    """
    Read a CSV file of name, shares, price data into a list of dicts.
    """
    portfolio: list[Holding] = []

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
            portfolio.append(
                Holding(name=name, shares=int(shares), price=Decimal(price))
            )

    return portfolio


def make_report(portfolio: list[Holding]):
    """
    Print a report
    """
    portfolio.sort(key=lambda h: h.value(), reverse=True)
    print(" " + "-" * (WIDTH * 4 + 3))
    print(
        f"|{'name':^{WIDTH}}|{'shares':^{WIDTH}}|{'price':^{WIDTH}}|{'value':^{WIDTH}}|"
    )
    print("|" + ("-" * WIDTH + "|") * 4)

    total_value = 0
    for holding in portfolio:
        value = holding.value()
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
