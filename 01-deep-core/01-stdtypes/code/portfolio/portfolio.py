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
