from exercise_02 import Calculator, test_calculator

"""
类装饰器一下就处理多个方法
"""


def add_stack_debug(cls):
    orig_push = cls.push

    def push(self, item):
        print("PUSHED:", item)
        orig_push(self, item)

    cls.push = push

    orig_pop = cls.pop

    def pop(self):
        item = orig_pop(self)
        print("POPPED:", item)
        return item

    cls.pop = pop

    return cls


def add_stack_checking(cls):
    orig_push = cls.push

    def push(self, item):
        if not isinstance(item, (int, float)):
            raise TypeError("Require a number")
        orig_push(self, item)

    cls.push = push
    return cls


@add_stack_debug
@add_stack_checking
class MyCalculator(Calculator):
    pass


if __name__ == "__main__":
    calc = MyCalculator()
    test_calculator(calc)
