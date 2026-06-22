class Theater:
    def __init__(
        self,
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

    def compute_attendees(self, price):
        return (
            self._base_attendees
            - (price - self._base_price) * self._attendees_per_dollar
        )

    def compute_cost(self, attendees):
        return self._fixed_cost + self._cost_per_attendee * attendees

    def compute_profit(self, price): ...
