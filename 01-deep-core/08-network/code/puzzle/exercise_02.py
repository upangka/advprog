from exercise_01 import print_apartment


class Fail(Exception):
    pass


def require(test):
    if not test:
        raise Fail()


def forbid(test):
    require(not test)


def distinct(*args):
    return len(args) == len(set(args))


def adjacent(x, y):
    return abs(x - y) == 1


def better_force():
    import itertools

    for baker, cooper, fletcher, miller, smith in itertools.product(
        range(1, 6), repeat=5
    ):
        try:
            require(distinct(baker, cooper, fletcher, miller, smith))
            require(baker != 5)
            require(cooper != 1)
            forbid(fletcher == 1 or fletcher == 5)
            require(miller > cooper)
            forbid(adjacent(smith, fletcher))
            forbid(adjacent(fletcher, cooper))
            print_apartment(baker, cooper, fletcher, miller, smith)
        except Fail:
            pass


if __name__ == "__main__":
    better_force()
