import itertools

from exercise_03 import Fail, forbid, require


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
    require(1 <= floor <= 5)
    forbid(mode == "MOVINGDOWN" and floor == 1)
    forbid(mode == "MOVINGUP" and floor == 5)
    forbid(mode == "LOADINGDOWN" and floor == 1)
    forbid(mode == "LOADINGUP" and floor == 5)

    forbid(
        mode in {"MOVINGUP", "MOVINGDOWN"}
        and len(destinations + up_requests + down_requests) == 0
    )
    forbid(
        mode == "MOVINGUP" and max(destinations + up_requests + down_requests) <= floor
    )
    forbid(
        mode == "MOVINGDOWN"
        and min(destinations + up_requests + down_requests) >= floor
    )

    forbid(
        mode in {"LOADINGUP", "LOADINGDOWN"}
        and len(destinations + up_requests + down_requests) == 0
    )
    # 禁止电梯在 UNLOADING 模式时，当前楼层不在 destinations 中（没人要在这个楼层下电梯）
    forbid(mode == "UNLOADING" and destinations)
    forbid(mode == "LOADINGUP" and floor in up_requests)
    forbid(mode == "LOADINGDOWN" and floor in down_requests)
    forbid(mode in {"LOADINGUP", "LOADINGDOWN", "UNLOADING"} and floor in destinations)
    forbid(
        mode == "LOADINGUP" and max(destinations + up_requests + down_requests) <= floor
    )
    forbid(
        mode == "LOADINGDOWN"
        and min(destinations + up_requests + down_requests) >= floor
    )

    forbid(mode == "IDLE" and (destinations or up_requests or down_requests))


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
    size = len(elevators)
    print(size, "elevators")
    import random
    
    # print([item for item in elevators if item['mode']=="UNLOADING"])
    
    # 随机查看电梯随机数据
    count = 0
    while count < 10:
        r = elevators[random.randint(0,size-1)]
        if r['mode'] == "UNLOADING":
            print(r)
            count += 1

    # for _ in range(10):
    #     idx = random.randint(0, size-1)
    #     print(elevators[idx])


if __name__ == "__main__":
    main()


"""invalid elevator status
{'mode': 'MOVINGDOWN', 'floor': 1, 'destinations': (2, 3, 4), 'up_requests': (2, 3), 'down_requests': ()}
{'mode': 'LOADINGDOWN', 'floor': 2, 'destinations': (), 'up_requests': (3,), 'down_requests': (2, 3, 5)}
{'mode': 'UNLOADING', 'floor': 1, 'destinations': (), 'up_requests': (3,), 'down_requests': (2, 3, 5)}
"""
