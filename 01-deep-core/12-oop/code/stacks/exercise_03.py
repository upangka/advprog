import operator

from exercise_01 import Stack


class NotEnoughValues(Exception):
    pass


class Calculator:
    def __init__(self, *, stack=Stack()) -> None:
        self._stack = stack

    def _do_cal(self, op):
        if len(self._stack) < 2:
            raise NotEnoughValues(f"Not enough elements to support {op.__name__}")
        right = self.pop()
        left = self.pop()
        r = op(left, right)
        self._stack.push(r)
        return r

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

    def with_stack(self, stack: Stack):
        self._stack = stack


def test_failure(calc):
    calc.push(23)
    try:
        calc.add()  # should fail. Not enough values were pushed
    except Exception as err:
        pass
    else:
        raise AssertionError("Why didn't I fail???")
    # What happens if you resume using the calculator after a failure?
    calc.push(45)
    calc.add()  # Does this work?
    assert calc.pop() == 68  # Does this work?
    print("Good test")


if __name__ == "__main__":
    test_failure(Calculator())
