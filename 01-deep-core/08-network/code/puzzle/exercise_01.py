"""
1. 楼层编号: 这栋楼有 5 层,从下到上编号为 1 到 5(1 是底层,5 是顶层).
2. 互不相同: Baker、Cooper、Fletcher、Miller、Smith 五个人分别住在不同的楼层(没有两个人住在同一层).
3. Baker(贝克): 不住在顶层(即不住在 5 楼).
4. Cooper(库珀): 不住在底层(即不住在 1 楼).
5. Fletcher(弗莱彻): 既不住在顶层(5 楼),也不住在底层(1 楼).
6. Miller(米勒): 住的楼层比 Cooper 更高(即 Miller 的楼层号 > Cooper 的楼层号).
7. Smith(史密斯): 住的楼层不挨着 Fletcher(即两人的楼层号之差的绝对值 ≠ 1).
8. Fletcher(弗莱彻): 住的楼层不挨着 Cooper(即两人的楼层号之差的绝对值 ≠ 1).
"""

import operator


def print_apartment(baker, cooper, fletcher, miller, smith):
    results = [
        (baker, "Baker"),
        (cooper, "Cooper"),
        (fletcher, "Fletcher"),
        (miller, "Miller"),
        (smith, "Smith"),
    ]
    # Print a solution
    [
        print(f"<{floor}>-{name}")
        for floor, name in sorted(results, key=operator.itemgetter(0), reverse=True)
    ]


def brute_force():

    for baker in range(1, 6):
        for cooper in range(1, 6):
            for fletcher in range(1, 6):
                for miller in range(1, 6):
                    for smith in range(1, 6):
                        # All must live on different floors.
                        if len({baker, cooper, fletcher, miller, smith}) != 5:
                            continue
                        # ... more constraints ...

                        # Baker not on top floor
                        if baker == 5:
                            continue
                        # Cooper not on bottom floor
                        if cooper == 1:
                            continue
                        # Fletcher not on top or bottom
                        if fletcher in {1, 5}:
                            continue
                        # Miller lives on higher floor than cooper
                        if miller < cooper:
                            continue
                        # Smith not adjacent to Fletcher
                        if abs(smith - fletcher) == 1:
                            continue
                        # Fletcher not adjacent to Cooper
                        if abs(cooper - fletcher) == 1:
                            continue

                        # Print a solution
                        print_apartment(baker, cooper, fletcher, miller, smith)


if __name__ == "__main__":
    brute_force()
