class Theater:

    def __init__(
        self,
        # Put the variables in class
        base_price: float = 5.0,  # Dollars
        base_attendees: int = 120,  # Number of attendees at base price
        attendees_per_dollar: int = 150,  # 150 people per dollar (15 people per 0.10 dollar)
        fixed_cost: float = 180.0,  # Dollars
        cost_per_attendee: float = 0.04,  # Dollars
    ):
        self._base_price = base_price
        self._base_attendees = base_attendees
        self._attendees_per_dollar = attendees_per_dollar
        self._fixed_cost = fixed_cost
        self._cost_per_attendee = cost_per_attendee

    def _compute_attendees(self, price):
        return (
            self._base_attendees
            - (price - self._base_price) * self._attendees_per_dollar
        )

    def _compute_cost(self, attendees):
        return self._fixed_cost + self._cost_per_attendee * attendees

    def compute_profit(self, price):
        num_attendees = self._compute_attendees(price)
        revenue = price * num_attendees
        cost = self._compute_cost(num_attendees)
        return revenue - cost


# Global variables
LOW_PRICE = 1.0
HIGH_PRICE = 9.0
INCREMENT = 0.10


def find_ticket_price():  # "Wishful thinking" (top-down approach)
    theater = Theater()
    price = LOW_PRICE
    best_price = price
    best_profit = theater.compute_profit(best_price)

    while price <= HIGH_PRICE:
        price += INCREMENT
        profit = theater.compute_profit(price)
        if profit >= best_profit:
            best_profit = profit
            best_price = price
    return best_price


if __name__ == "__main__":
    best_profit = find_ticket_price()
    print(f"The best profit for the theater is: {best_profit}")
    # round(number,ndigits) ndigits 控制精度到小数后两位
    print(f"The best profit for the theater is: {round(best_profit,2):.2f}")
