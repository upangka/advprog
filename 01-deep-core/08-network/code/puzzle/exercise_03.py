import itertools

from exercise_01 import print_apartment
from exercise_02 import Fail, adjacent, distinct, forbid, require


def apartment(baker, cooper, fletcher, miller, smith):
    require(distinct(baker, cooper, fletcher, miller, smith))
    require(baker != 5)
    require(cooper != 1)
    forbid(fletcher == 1 or fletcher == 5)
    require(miller > cooper)
    forbid(adjacent(smith, fletcher))
    forbid(adjacent(fletcher, cooper))


domain = {
    "baker": range(1, 6),
    "cooper": range(1, 6),
    "fletcher": range(1, 6),
    "miller": range(1, 6),
    "smith": range(1, 6),
}


def find_solutions(apartment, domain):
    solutions = []
    for values in itertools.product(*domain.values()):
        try:
            kwargs = dict(zip(domain.keys(), values))
            apartment(**kwargs)
            solutions.append(values)
        except Fail:
            pass
    return solutions


def main():
    solutions = find_solutions(apartment, domain)
    for soln in solutions:
        print_apartment(*soln)


if __name__ == "__main__":
    main()
