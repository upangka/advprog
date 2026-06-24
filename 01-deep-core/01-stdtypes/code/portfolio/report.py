import portfolio

width = 15


def make_report(portfolio: list):
    """
    Print a report
    """
    portfolio.sort(key=lambda h: h["shares"] * h["price"], reverse=True)
    print(" " + "-" * (width * 4 + 3))
    print(
        f"|{'name':^{width}}|{'shares':^{width}}|{'price':^{width}}|{'value':^{width}}|"
    )
    print("|" + ("-" * width + "|") * 4)

    total_value = 0
    for holding in portfolio:
        value = holding["shares"] * holding["price"]
        total_value += value
        name, shares, price = holding["name"], holding["shares"], holding["price"]
        print(
            f"|{name:^{width}s}|{shares:^{width}d}|{price:^{width}.2f}|{value:^{width}.2f}|"
        )
    print(" " + "-" * (width * 4 + 3))
    print(f"\nTotal value: {total_value:.2f}")


def main():
    port = portfolio.read_portfolio()
    make_report(port)


if __name__ == "__main__":
    main()
