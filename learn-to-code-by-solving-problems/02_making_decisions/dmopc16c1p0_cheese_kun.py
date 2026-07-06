from typing import Literal

M: Literal["absolutely", "fairly", "very"] = "very"
units, extra_cheese = [int(input()) for _ in range(2)]

# 1. `absolutely` satisfied:  if the pizza she gets has a width of 3
# units and an extra-cheesiness of at least 95%

# 2. `fairly` satisfied: if the pizza she gets has a width of 1
# unit and an extra-cheesiness of at most 50%

# `very` satisfied: with any other pizza she receives.

if units == 3 and extra_cheese >= 95:
    M = "absolutely"
elif units == 1 and extra_cheese <= 50:
    M = "fairly"

print(f"C.C. is {M} satisfied with her pizza.")
