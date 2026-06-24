import pandas
from pathlib import Path


class PandasPortfolio: 
    def __init__(self,df: pandas.DataFrame) -> None:
        self._df = df

    def __iter__(self):
        ...
        
    def total_value(self):
        ...

def read_portfolio(file_name="portfolio.csv"):
    if "__file__" in globals():
        file_path = Path(__file__).parent / file_name
    else:
        file_path = Path(file_name)
    return pandas.read_csv(file_path)
