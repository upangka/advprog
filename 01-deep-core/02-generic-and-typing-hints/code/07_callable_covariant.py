from collections.abc import Callable

# 从 Python 3.9 开始，typing.Callable 被标记为已弃用
# from typing import Callable


def update(probe: Callable[[], float], display: Callable[[float], None]):
    temperature = probe()
    # ...something else ...
    display(temperature)


# covariant 协变 int 可以传递给 float


def probe_ok() -> int:
    return 42


def display_error(temperature: int):
    print(hex(temperature))


def display_ok(temperature: complex):
    print(temperature)


# cotravariant 逆变 一个float -> 传递给接受的int，不能处理
# update(probe_ok, display_error)  # type error
# cotravariant 逆变 float consistent with 兼容complex,complex能够接受float
update(probe_ok, display_ok)
