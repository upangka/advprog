from dataclasses import dataclass
from decimal import Decimal


@dataclass
class Record:
    name: str
    shares: int
    price: Decimal


class DataParser:
    def parse(self, lines):
        for line in lines:
            row = line.split(",")
            # 需要子类实现的
            yield self.make_record(row)

    def make_record(self, row):
        # NotImplementedError
        # 就是专门为"子类必须实现的方法"设计的。
        raise NotImplementedError("Subclasses must override make_record()")


class PortfolioDataParser(DataParser):
    """子类专门进行实现"""

    def make_record(self, row):
        return Record(name=row[0], shares=int(row[1]), price=Decimal(row[2]))


def get_lines(filename="portfolio.csv"):
    with open(filename, mode="rt", encoding="utf-8") as f:
        yield from f


if __name__ == "__main__":
    parser = PortfolioDataParser()
    for r in parser.parse(get_lines()):
        print(f"{r.name:>10}|{r.shares:>10}|{r.price:>10}|")

"""
$ uv run exercise_05.py 
        AA|       100|     32.20|
       IBM|        50|     91.10|
       CAT|       150|     83.44|
      MSFT|       200|     51.23|
        GE|        95|     40.37|
      ACME|        50|     65.10|
       YOW|       100|     70.44|
"""
