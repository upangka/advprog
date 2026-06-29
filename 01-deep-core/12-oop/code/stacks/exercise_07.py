import math
import operator
from typing import Any, NamedTuple

from exercise_03 import NotEnoughValues


class OptMember(NamedTuple):
    left: Any
    right: Any


class Calculator:

    def __init__(self):
        self._items = []

    def pop(self):
        return self._items.pop()

    def push(self, item):
        self._items.append(item)

    def _pop2(self) -> OptMember:
        if len(self._items) < 2:
            raise NotEnoughValues("Not enough elements")

        return OptMember(right=self.pop(), left=self.pop())

    def _apply_binary(self, opt):
        """处理二元运算"""
        r = opt(*self._pop2())
        self.push(r)
        return r

    def _apply_uanry(self, opt):
        """处理一元运算"""
        r = opt(self.pop())
        self.push(r)
        return r

    def add(self):
        return self._apply_binary(operator.add)

    def sub(self):
        return self._apply_binary(operator.sub)

    def mul(self):
        return self._apply_binary(operator.mul)

    def div(self):
        return self._apply_binary(operator.truediv)

    def pow(self):
        return self._apply_binary(math.pow)

    def sqrt(self):
        return self._apply_unary(math.sqrt)

    def swap(self):
        operands = self._pop2()
        self.push(operands.right)
        self.push(operands.left)

    def __repr__(self):
        return f"Calculator({self._items})"


def test_calculator(calc):

    calc.push(23)
    calc.push(45)
    calc.add()
    assert calc.pop() == 68

    calc.push(2)
    calc.push(3)
    calc.push(4)
    calc.add()
    calc.mul()
    assert calc.pop() == 14

    calc.push(10)
    calc.push(3)
    calc.sub()
    assert calc.pop() == 7

    calc.push(10)
    calc.push(5)
    calc.div()
    assert calc.pop() == 2.0

    calc.push(10)
    calc.push(2)
    calc.pow()
    assert calc.pop() == 100

    calc.push(100)
    calc.sqrt()
    assert calc.pop() == 10.0

    calc.push(2)
    calc.push(3)
    calc.swap()
    assert calc.pop() == 2
    assert calc.pop() == 3

    # make sure that only numeric values can be pushed
    try:
        calc.push("two")
    except TypeError as err:
        pass
    else:
        assert False, "Bad Calculator!"
    print("Good Calculator")


if __name__ == "__main__":
    pass
    # test_calculator(Calculator())
