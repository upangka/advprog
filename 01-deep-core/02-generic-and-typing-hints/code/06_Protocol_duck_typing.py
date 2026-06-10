from collections.abc import Iterable
from typing import Protocol, TypeVar


class SupportLessThan(Protocol):
    # / 前面的参数只能按位置传入
    def __lt__(self, other, /) -> bool: ...


LT = TypeVar("LT", bound=SupportLessThan)
# def top(series: Iterable[SupportLessThan], n: int) -> list[SupportLessThan]:


def top(series: Iterable[LT], n: int) -> list[LT]:
    ordered = sorted(series, reverse=True)
    return ordered[:n]


top_a = top([4, 1, 5], 2)
fruits = "mango pear apple kiwi banana".split()
fruits_with_len = [(len(fruit), fruit) for fruit in fruits]
top_fruits = top(fruits_with_len, 2)

print(f"{top_a=}\n{top_fruits=}")
