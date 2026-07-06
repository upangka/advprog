cups = [True, False, False]


def move_A():
    cups[0], cups[1] = cups[1], cups[0]


def move_B():
    cups[1], cups[2] = cups[2], cups[1]


def move_C():
    cups[0], cups[2] = cups[2], cups[0]


def noop():
    pass


for t in input():
    func = globals().get(f"move_{t}", noop)
    func()

print(cups.index(True) + 1)
