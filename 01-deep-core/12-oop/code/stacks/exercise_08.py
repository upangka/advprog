import math
import operator

from exercise_03 import NotEnoughValues
from exercise_06 import add_stack_checking
from exercise_07 import OptMember


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

    def _apply_unary(self, opt):
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

    def run(self, commands: list[tuple[str,] | tuple[str, int]]):
        """
        问题的参数个数不同，怎么处理???
        """
        for cmd, *args in commands:
            getattr(self, cmd)(*args)


hypot = [("push", 2), ("pow",), ("swap",), ("push", 2), ("pow",), ("add",), ("sqrt",)]


def test_hypot():
    calc = Calculator()
    calc.push(3)
    calc.push(4)

    calc.run(hypot)
    assert calc.pop() == 5.0
    print("Good Script!")
