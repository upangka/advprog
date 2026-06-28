import operator

from exercise_01 import Stack


class Calculator:

    def __init__(self) -> None:
        self._stack = Stack(container=[])

    def _do_cal(self, op):
        right = self.pop()
        left = self.pop()
        r = op(left, right)
        self._stack.push(r)
        return r

    # 必须要有push和pop方法，因为后面的练习，monkey patching会用到
    def push(self, item):
        self._stack.push(item)

    def pop(self):
        return self._stack.pop()

    def add(self):
        return self._do_cal(operator.add)

    def sub(self):
        return self._do_cal(operator.sub)

    def mul(self):
        return self._do_cal(operator.mul)

    def div(self):
        return self._do_cal(operator.truediv)


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
    print("Good calculator!")


if __name__ == "__main__":
    test_calculator(Calculator())
