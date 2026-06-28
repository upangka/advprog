import operator

from exercise_01 import Stack


class Calculator(Stack):

    def __init__(self) -> None:
        super().__init__(container=[])

    def _do_cal(self, op):
        first_top = self.pop()
        second_top = self.pop()
        r = op(second_top, first_top)
        self.push(r)
        return r

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
