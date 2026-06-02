
"""Exercise 07 Only a Calculator

Define a Calculator class that has the same functionality as before,
but which doesn't bother with all of the extra stack class code.
While we're at it, we might as well give the calculator a few
extra functions like square roots, powers, swapping stack items
and so forth.  Your class should pass the tests below.
"""


import math


class NotEnoughValues(Exception):
    pass


class Calculator:

    def __init__(self):
        self._values = []

    def push(self, item):
        if not isinstance(item, (int, float)):
            raise TypeError("Only accept number")

        self._values.append(item)

    def pop(self):
        return self._values.pop()

    def _pop2(self):
        if len(self._values) < 2:
            raise NotEnoughValues("Not enough values")

        return (self.pop(), self.pop())

    def add(self):
        right, left = self._pop2()
        self.push(right + left)

    def sub(self):
        right, left = self._pop2()
        self.push(left - right)

    def mul(self):
        right, left = self._pop2()
        self.push(right * left)

    def div(self):
        right, left = self._pop2()
        self.push(left / right)

    def pow(self):
        exp, base = self._pop2()
        self.push(base ** exp)

    def sqrt(self):
        if len(self._values) < 1:
            raise NotEnoughValues()
        self.push(math.sqrt(self.pop()))

    def swap(self):
        right, left = self._pop2()
        self.push(right)
        self.push(left)

    # exercise 08
    def run(self, instructions):
        for instruction in instructions:
            cmd = instruction[0]
            args = instruction[1:]

            if cmd == 'push':
                self.push(args[0])
            elif cmd == 'add':
                self.add()
            elif cmd == 'sub':
                self.sub()
            elif cmd == 'mul':
                self.mul()
            elif cmd == 'div':
                self.div()
            elif cmd == 'pow':
                self.pow()
            elif cmd == 'sqrt':
                self.sqrt()
            elif cmd == 'swap':
                self.swap()
            else:
                raise ValueError(f"Unknown instruction: {cmd}")


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


test_calculator(Calculator())


r"""Exercise 08 The Script

Mel wants to know if common calculations can be "scripted" or memorized in
some way.  For example, a common task in math class is to compute
the length of the hypotenuse of a triangle.

      |\
      | \
      |  \
      |   \
  x   |    \   hypot = sqrt(x**2 + y**2)
      |     \
      |      \
      |       \
      |________\
          y

Mel has written out a list of "instructions" that carry out this
operation, assuming that the values of "x" and "y" have already been
entered.  Could you give the `Calculator` class a "run" method that
executes the instructions one after the other?  That is your task.
"""
