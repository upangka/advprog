import math

radius = float(input())
height = float(input())


def volume(radius: float, height: float) -> float:
    return math.pi * radius**2 * height / 3


print(f"{volume(radius,height):.3f}")
