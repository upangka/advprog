from exercise_01 import Stack
from exercise_02 import test_calculator
from exercise_03 import Calculator


# An implementation of a Stack with debugging
class DebugStack(Stack):
    def push(self, item):
        print("PUSHING:", item)
        # 注意这里，super不一定就是指这里继承的Stack
        super().push(item)

    def pop(self):
        item = super().pop()
        print("POPPED:", item)
        return item


class NumericStack(Stack):
    def push(self, item):
        if not isinstance(item, (int, float)):
            raise TypeError("A number is required")
        super().push(item)


if __name__ == "__main__":
    # Approch 1: Monkey patching
    calc = Calculator()
    calc._stack = DebugStack()
    test_calculator(calc)
