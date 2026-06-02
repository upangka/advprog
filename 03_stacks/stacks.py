"""Exercise 01
- Clarity interface
- Don't expose the internal error
"""


class Stack:
    def __init__(self):
        self._items = []

    def push(self, item):
        self._items.append(item)

    def pop(self):
        if not self._items:
            raise StackEmptyError("Pop from an empty stack")
        return self._items.pop()

    def __len__(self):
        return len(self._items)


class StackEmptyError(Exception):
    pass


def test_stack(s):
    s.push(3)
    s.push(6)

    assert len(s) == 2
    assert s.pop() == 6
    assert s.pop() == 3
    assert len(s) == 0
    print(f"Good {type(s).__name__}")


test_stack(Stack())


"""Exercise 02
- How is this related to Stack?
    1. Composition: Calculator has a stack inside as a component
    2. Inheritance code reuse (using functionality from Stack)
"""


class NotEnoughValues(Exception):
    pass


class Calculator:

    # Composition
    # Generally prefer composition (most of the time)
    def __init__(self, stack=None):
        if not stack:
            stack = Stack()
        self._stack = stack

    def push(self, value):
        self._stack.push(value)

    def pop(self):
        return self._stack.pop()

    def _with_stack(self, stack):
        """Helper to allow the internal stack to be replaced
        General problem: Dependency Injection. (Calculators depends on Stack)
        """
        self._stack = stack
        return self

    def _pop2(self):
        """Help method for math ops

        Returns:
            Pop 2 values from stack or error
        """
        if len(self._stack) < 2:
            raise NotEnoughValues("Not enough values")

        return (self.pop(), self.pop())

    def add(self):
        right, left = self._pop2()
        self.push(left + right)

    def sub(self):
        right, left = self._pop2()
        self.push(left - right)

    def mul(self):
        right, left = self._pop2()
        self.push(left * right)

    def div(self):
        right, left = self._pop2()
        self.push(left // right)


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
    print(f"Good {type(calc).__name__}")


calc = Calculator()
test_calculator(calc)


"""Exercise 03 The Mutable
A central idea of object-oriented programming is that it is often focused
on behavior and mutation.  You create an object.  You execute methods on the
object.  Those methods tend to modify the state of the object.

However, what happens when a method fails?:

Check this out: a tuple is immutable, but if you modify a list inside it, the tuple still updates to include the new items

>>> t = (1,2,[3,4])
>>> t[2] += [100,101,102]
Traceback (most recent call last):
  File "<python-input-6>", line 1, in <module>
    t[2] += [100,101,102]
    ~^^^
TypeError: 'tuple' object does not support item assignment
>>> t
(1, 2, [3, 4, 100, 101, 102])

YOUR TASK: Modify the calculator class so that its methods either work entirely or fail entirely. Methods that fail should leave the calculator state unchanged.
"""


def test_failure(calc):
    """
    Don't just care about what methods are there.
    Think about the whole object's behavior
    """
    calc.push(23)

    try:
        calc.add()
    except Exception as err:
        print(err)

    calc.push(45)
    calc.add()
    assert calc.pop() == 68
    print("Good Calculator with failure")


test_failure(Calculator())


"""Exercise 04 Debugged and the Defended

Peter is working on some code that involves the Calculator class. However, it's broken and he is trying to figure out why. 

To help dedug it,he's written a customized Stack class with some print statements added to it.

  Similarly, Arjoon has decided that the calculator should do a better job
  of type-checking.  "Why is this allowed?" he asks:
 
      >>> s = Stack()
      >>> s.push('hello')
      >>> s.push(4)
      >>> s.mul()
      >>> s.pop()
      'hellohellohellohello'
 
  To address this, he's created a custom Stack with some type-checking
  added to it.
 
  Although Peter and Arjoon, have created custom Stack classes, they're
  now both perplexed about how to use them with the Calculator class.
  How would you modify the Calculator class to allow alternative
  Stack implementations to be used?
"""

# An implementation of a Stack with debugging


class DebugStack(Stack):

    def push(self, value):
        print(f"PUSHING: {value}")
        super().push(value)

    def pop(self):
        item = super().pop()
        print(f"POPPED: {item}")
        return item


# An implementation of a "numeric" stack where items must be numbers
class NumericStack(Stack):

    def push(self, value):
        if not isinstance(value, (int, float)):
            raise TypeError("A number is required")
        super().push(value)


test_stack(DebugStack())
test_stack(NumericStack())

# Approach 1: Direct modification of internals
# Monkeypatching 运行时动态修改或替换已有的代码

calc = Calculator()
calc._stack = DebugStack()
test_calculator(calc)

# Approach 2: Some kind of more cotrolled way of accomplishing the same thing.
calc = Calculator()._with_stack(NumericStack())
test_calculator(calc)

# Approach 3: Would it make sense to make this part of the object
# constructor instead?
calc = Calculator()
test_calculator(calc)


"""Exercise 05 The Conflict

Both Peter and Arjoon have created alternative Stack implementations.
However, a debate has now erupted about how to enable the functionality
of *both* classes at the same time (that is, to have both type-checking
and debugging turned on all at once).

There seems to be no obviously "great" way to use two stacks at
once. However, Mary observes that both of these features could be
implemented as an "add-on" instead.

To illustrate, she's written the following classes below. Your
task: figure how theses classes are supposed to be used with either
the Stack or Calculator class to enable debugging and type checking
at the same time.
"""


class DebugStackOps:    # There is *NO* inheritance here

    def push(self, value):
        print(f"PUSHING: {value}")
        super().push(value)

    def pop(self):
        value = super().pop()
        print(f"POPPED: {value}")
        return value


class NumericPush:      # There is *NO* inheritance here

    def push(self, value):
        if not isinstance(value, (int, float)):
            raise TypeError("A number is required.")
        super().push(value)

# Typical usage: Adding optional features to objects in frameworks


class MyCalculator(DebugStackOps, NumericPush, Calculator):
    pass


"""Exercise 06 The Patch 

Instead of defining debugging and type checking features as classes,
Ben has proposed an approach involving code patching.  The functions below
have been written.  Show how you could use these functions to add
debugging and type-checking to the calculator at the same time.

Note: These functions can be used as class decorators, but they don't
necessarily have to be used exactly in that way.
"""


def add_stack_debug(cls):
    orig_push = cls.push

    def push(self, value):
        print(f"PUSHING: {value}")
        orig_push(self, value)

    cls.push = push

    orig_pop = cls.pop

    def pop(self):
        value = orig_pop(self)
        print(f"POPPED: {value}")
        return value

    cls.pop = pop

    return cls


def add_stack_checking(cls):
    orig_push = cls.push

    def push(self, value):
        if not isinstance(value, (int, float)):
            raise TypeError("A number is required")
        orig_push(self, value)

    cls.push = push
    return cls


@add_stack_debug
@add_stack_checking
class MyCalculator(Calculator):
    pass


calc = MyCalculator()
test_calculator(calc)
