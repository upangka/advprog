import itertools
from exercise_03 import Fail, require, forbid


def all_combinations(values):

    result = []
    for n in range(len(values) + 1):
        result.extend(itertools.combinations(values, n))
    return result


elevator_domain = {
    "mode": {"IDLE", "MOVINGUP", "MOVINGDOWN", "LOADINGUP", "LOADINGDOWN", "UNLOADING"},
    "floor": range(1, 6),
    "destinations": all_combinations(range(1, 6)),
    "up_requests": all_combinations(range(1, 5)),
    "down_requests": all_combinations(range(2, 6)),
}


def elevator_spec(mode, floor, destinations, up_requests, down_requests):
    # 245760 -> 245760
    require(1 <= floor <= 5)


def find_solutions(spec, domain):
    for values in itertools.product(*domain.values()):
        candidate = dict(zip(domain.keys(), values))
        try:
            spec(**candidate)
            yield candidate
        except Fail:
            pass


def main():
    elevators = list(find_solutions(elevator_spec, elevator_domain))
    print(len(elevators), "elevators")
    print(elevators[5760])


if __name__ == "__main__":
    main()


"""invalid elevator status
{'mode': 'MOVINGDOWN', 'floor': 1, 'destinations': (2, 3, 4), 'up_requests': (2, 3), 'down_requests': ()}
"""
