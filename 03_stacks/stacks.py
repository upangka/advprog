"""Exercise 01
- Clarity interface
- Don't expose the internal error
"""
class Stack:
    def __init__(self):
        self._items = []

    def push(self,item):
        self._items.append(item)

    def pop(self):
        if not self._items:
            raise StackEmptyError("Pop from an empty stack")
        return self._items.pop()
    
    def __len__(self):
        return len(self._items)


class StackEmptyError(Exception): pass

def test_stack(s):
    s.push(3)
    s.push(6)

    assert len(s) == 2
    assert s.pop() == 6
    assert s.pop() == 3
    assert len(s) == 0
    print("Good Luck")

test_stack(Stack())
   

"""Exercise 02
- How is this related to Stack?
    1. Composition: Calculator has a stack inside as a component
    2. Inheritance code reuse (using functionality from Stack)
"""

class Calculator:
    
    # Composition
    # Generally prefer composition (most of the time)
    def __init__(self):
        self._stack = Stack()


    def push(self,value):
        self._stack.push(value)

    def pop(self):
        return self._stack.pop()


    def add(self):
        right = self.pop()
        left = self.pop()
        self.push(left + right)

    def sub(self):
        right = self.pop()
        left = self.pop()
        self.push(left - right)

    def mul(self):
        right = self.pop()
        left = self.pop()
        self.push(left * right)

    def div(self):
        right = self.pop()
        left = self.pop()
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
    print("Good calculator!")


calc = Calculator()
test_calculator(calc)


"""Exercise 03 The Mutable
A central idea of object-oriented programming is that it is often focused
on behavior and mutation.  You create an object.  You execute methods on the
object.  Those methods tend to modify the state of the object.

However, what happens when a method fails?  Consider the following test
involving a calculator:

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
"""











