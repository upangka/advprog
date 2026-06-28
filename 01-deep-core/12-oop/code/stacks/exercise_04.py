from exercise_01 import Stack
from exercise_02 import test_calculator
from exercise_03 import Calculator


# An implementation of a Stack with debugging
class DebugStack(Stack):
    def push(self, item):
        print("PUSHING:", item)
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
    # Approach 1: Monkey patching
    calc = Calculator()
    calc._stack = DebugStack()
    test_calculator(calc)

    # Approach 2: Some kind of more controlled way of accomplishing the same thing
    calc = Calculator()
    calc.with_stack(DebugStack())
    test_calculator(calc)

    # Approach 3: Make this part of the object constructor instead.
    calc = Calculator(stack=DebugStack())
    test_calculator(calc)
