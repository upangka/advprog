from array import array
from collections import deque
from collections.abc import MutableSequence
from typing import Optional


class Stack:
    def __init__(self, *, container: Optional[MutableSequence] = None):
        self._items = container if container else []

    def push(self, item):
        self._items.append(item)

    def pop(self):
        return self._items.pop()


def test_cotainer(container=None):
    s = Stack(container=container)
    s.push(1)
    s.push(2)
    s.push(3)
    r = [s.pop() for _ in range(3)]
    assert r == [3, 2, 1]
    print("Good Test")


if __name__ == "__main__":
    test_cotainer()
    test_cotainer(array("i"))
    test_cotainer(deque())
