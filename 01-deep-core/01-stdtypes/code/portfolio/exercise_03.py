from decimal import Decimal
from pathlib import Path

import pandas

from exercise_02 import Holding, make_report


class PandasPortfolio:
    def __init__(self, df: pandas.DataFrame):
        self._df = df
        self._df["price"] = df["price"].map(lambda x: Decimal(str(x)))
        self._df["value"] = df["shares"] * df["price"]

    def __iter__(self):
        for _, row in self._df.iterrows():
            yield Holding(
                name=row["name"], shares=row["shares"], price=Decimal(str(row["price"]))
            )

    def total_value(self):
        return self._df["value"].sum()


def read_portfolio(file_name="portfolio.csv"):
    if "__file__" in globals():
        file_path = Path(__file__).parent / file_name
    else:
        file_path = Path(file_name)
    return pandas.read_csv(file_path, skipinitialspace=True)


def main():
    port = PandasPortfolio(read_portfolio())
    make_report(port)


if __name__ == "__main__":
    main()
