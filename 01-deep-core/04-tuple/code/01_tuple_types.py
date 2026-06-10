from collections.abc import Sequence
from typing import NamedTuple, Optional


def display1(lat_lon: tuple[float, float]) -> str:
    lat, lon = lat_lon
    ns = "N" if lat >= 0 else "S"
    ew = "E" if lon >= 0 else "W"
    return f"{abs(lat):.1f}°{ns}, {abs(lon):.1f}°{ew}"


# Tuple as records
print(display1((22.5431, 114.0579)))


class Coordinate(NamedTuple):
    lat: float
    lon: float


# NamedTuple consistent-with tuple[float,float]
print(display1(Coordinate(22.5431, 114.0579)))


def display2(lat_lon: Coordinate) -> str:
    lat, lon = lat_lon
    ns = "N" if lat >= 0 else "S"
    ew = "E" if lon >= 0 else "W"
    return f"{abs(lat):.1f}°{ns}, {abs(lon):.1f}°{ew}"


# The reverse is not true
# tuple[float,float] is not consistent-with Coordinate
# print(display2((22.5431, 114.0579)))

"""
Tuple as immutable sequence
"""

fruits = "苹果 香蕉 葡萄 橘子 樱桃 柠檬 石榴 椰子 榴莲 甘蔗 山楂 蓝莓".split()


def columnize(
    sequence: Sequence[str], columns: Optional[int] = None
) -> list[tuple[str, ...]]:
    if columns is None or columns < 1:
        columns = round(len(sequence) ** 0.5)
    rows, reminder = divmod(len(sequence), columns)
    rows += bool(reminder)
    # 切片并不会数组越界
    return [tuple(sequence[i::rows]) for i in range(rows)]
