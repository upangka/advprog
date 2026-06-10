from collections.abc import Iterable
from typing import TypeAlias

# type FromTo = tuple[str, str]
FromTo: TypeAlias = tuple[str, str]


def zip_replace(text: str, changes: Iterable[FromTo]) -> str:
    for from_, to in changes:
        text = text.replace(from_, to)
    return text


l33t = [("a", "4"), ("e", "3"), ("l", "1"), ("o", "0"), ("s", "5"), ("t", "7")]
print(zip_replace("hello world", l33t))
