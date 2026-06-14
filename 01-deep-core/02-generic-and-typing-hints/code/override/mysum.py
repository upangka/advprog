MISSING = object()
MSG = "max() arg is an empty sequence"

from typing import overload, Protocol, TypeVar, Union
from collections.abc import Iterable, Callable


class SupportLessThan(Protocol):
    def __lt__(self, other, /) -> bool: ...


T = TypeVar("T")
LT = TypeVar("LT", bound=SupportLessThan)
DT = TypeVar("DT")


# 支持__lt__,但是没有key和default,注意这里是怎么禁用传入key的
@overload
def mymax(_arg1: LT,/, *args: LT, key: None = ...) -> LT: ...
# 等价
@overload
def mymax(first: LT, *args: LT, key: None = ...) -> LT: ...
@overload
def mymax(_iterable: Iterable[LT], /, *, key: None = ...) -> LT: ...
@overload
def mymax(_iterable: Iterable[T], /, *, key: Callable[[T], LT]) -> T: ...
@overload
def mymax(_arg1: T,/, *args: T, key: Callable[[T], LT]) -> T: ...
@overload
def mymax(
    _iterable: Iterable[LT], /, *, key: None = ..., default: DT
) -> Union[LT, DT]: ...  # 有默认值但是没有key
@overload
def mymax(
    _iterable: Iterable[T], /, *, key: Callable[[T], LT], default: DT
) -> Union[T, DT]: ...  # 有默认值也有key
def mymax(first, *args, key=None, default=MISSING):
    if args:
        series = args
        candidate = first
    else:
        series = iter(first)
        try:
            candidate = next(series)
        except StopIteration:
            if default is not MISSING:
                return default
            raise ValueError

    if key:
        # assert callable(key),"Not callable"
        candidate_key = key(candidate)
        for current in series:
            current_key = key(current)
            if current_key > candidate_key:
                candidate_key = current_key
                candidate = current
    else:
        for current in series:
            if current > candidate:
                candidate = current
    return candidate


mymax([2, 3, 5, -100], key=abs)
mymax(1, 2, 3, 4, 5, 6, -7, key=abs)
