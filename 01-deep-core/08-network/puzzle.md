
Graydon decides that he's going to go visit his friend Fletcher. He arrives at the apartment building and gets in the elevator. However, instead of a typical building directory, the following puzzle is posted:

Baker, Cooper, Fletcher, Miller, and Smith live here. They each live on a different floor. Baker does not live on the top floor. Cooper does not live on the bottom floor. Fletcher does not live on either the top or the bottom floor. Miller lives on a higher floor than does Cooper. Smith does not live on a floor adjacent to Fletcher. Fletcher does not live on a floor adjacent to Cooper.

1. 楼层编号: 这栋楼有 5 层,从下到上编号为 1 到 5(1 是底层,5 是顶层).
2. 互不相同: Baker、Cooper、Fletcher、Miller、Smith 五个人分别住在不同的楼层(没有两个人住在同一层).
3. Baker(贝克): 不住在顶层(即不住在 5 楼).
4. Cooper(库珀): 不住在底层(即不住在 1 楼).
5. Fletcher(弗莱彻): 既不住在顶层(5 楼),也不住在底层(1 楼).
6. Miller(米勒): 住的楼层比 Cooper 更高(即 Miller 的楼层号 > Cooper 的楼层号).
7. Smith(史密斯): 住的楼层不挨着 Fletcher(即两人的楼层号之差的绝对值 ≠ 1).
8. Fletcher(弗莱彻): 住的楼层不挨着 Cooper(即两人的楼层号之差的绝对值 ≠ 1).

"What button do I press?", he asks.

# Exercise 1 - Brute force

Write a program that finds out where everyone lives.

To do this, think about a brute force solution. You know that Baker, Cooper, Fletcher, Miller, and Smith all live on different floors.
One way to solve it would be to cycle through all permutations of the floors and to enforce the various rules as a series of constraints. Print out the solutions that pass them all.

Is there only one solution? Multiple solutions? No solutions?

[exercise_01.py](./code/puzzle/exercise_01.py)

```python
def brute_force():
    import operator

    for baker in range(1, 6):
        for cooper in range(1, 6):
            for fletcher in range(1, 6):
                for miller in range(1, 6):
                    for smith in range(1, 6):
                        # All must live on different floors.
                        if len({baker, cooper, fletcher, miller, smith}) != 5:
                            continue
                        # ... more constraints ...
                        if baker == 5:
                            continue
                        if cooper == 1:
                            continue
                        if fletcher in {1, 5}:
                            continue
                        if miller < cooper:
                            continue
                        if abs(smith - fletcher) == 1:
                            continue
                        if abs(cooper - fletcher) == 1:
                            continue

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
                            for floor, name in sorted(
                                results, key=operator.itemgetter(0), reverse=True
                            )
                        ]
```

输出：

```sh
<5>-Miller
<4>-Fletcher
<3>-Baker
<2>-Cooper
<1>-Smith
```