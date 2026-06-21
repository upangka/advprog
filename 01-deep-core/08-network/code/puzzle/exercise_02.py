class Fail(Exception):
    pass


def better_force():
    import operator

    for baker in range(1, 6):
        for cooper in range(1, 6):
            for fletcher in range(1, 6):
                for miller in range(1, 6):
                    for smith in range(1, 6):
                        try:
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
                        except Fail as e:
                            ...


if __name__ == "__main__":
    better_force()
