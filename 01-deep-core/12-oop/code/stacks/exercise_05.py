from exercise_02 import Calculator, test_calculator


class DebugStackOps:
    """注意这里没有继承Stack"""

    def push(self, item):
        print("PUSHED:", item)
        super().push(item)

    def pop(self):
        item = super().pop()
        print("POPPED:", item)
        return item


class NumericPush:
    """同样的这里也没有继承"""

    def push(self, item):
        if not isinstance(item, (int, float)):
            raise TypeError("Require a number")
        super().push(item)


class MyCalculator(DebugStackOps, NumericPush, Calculator):
    """
    当调用pop的时候，顺着链去找，DebugStackOps.pop -> NumericPush X没找到
    -> 继续到Calculator中找pop,直到找到为止
    """

    pass


if __name__ == "__main__":
    test_calculator(MyCalculator())
