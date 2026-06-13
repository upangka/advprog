from collections.abc import Sequence
from typing import TypeVar, overload


class Vowels(Sequence[str]):
    @overload
    def __getitem__(self, index: int) -> str: ...

    @overload
    def __getitem__(self, index: slice) -> Sequence[str]: ...

    def __getitem__(self, index) -> str | Sequence[str]:
        return "aeiou"[index]

    def __len__(self):
        return 5


# 名义结构化要求必须是继承关系
S = TypeVar("S")


def check_sequence1(s: Sequence[S]):
    # 迭代器支持 __iter__
    for item in s:
        print(item, end="", flush=True)
    print()
    # __contains__ 支持
    print("a" in s)
    import random

    return s[0] if random.random() > 0.3 else s[0:2]


a = check_sequence1(Vowels())  # a: str | Sequence[str]


class Ano:
    def __getitem__(self, i):
        return "aeiou"[i]


# b = check_sequence1(Ano())
