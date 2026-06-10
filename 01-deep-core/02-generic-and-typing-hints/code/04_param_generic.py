from collections.abc import Sequence
from random import shuffle
from typing import TypeVar

# T is a type variable
# that will be bound to a specific type with each usage.
T = TypeVar("T", int, float, str)


def sample(population: Sequence[T], size: int) -> list[T]:
    if size < 1:
        raise ValueError("size must be positive")
    result = list(population)
    shuffle(result)
    return result[:size]


result = sample("hello world", 3), sample([1, 2, 3, 4, 5], 3)
