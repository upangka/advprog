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

class NotEnoughValues(Exception): pass

class Calculator:
    
    # Composition
    # Generally prefer composition (most of the time)
    def __init__(self):
        self._stack = Stack()


    def push(self,value):
        self._stack.push(value)

    def pop(self):
        return self._stack.pop()


    def _pop2(self):
        """Help method for math ops
        
        Returns:
            Pop 2 values from stack or error
        """
        if len(self._stack) < 2:
            raise NotEnoughValues("Not enough values")

        return (self.pop(),self.pop())
    
    def add(self):
        right,left = self._pop2()
        self.push(left + right)

    def sub(self):
        right,left = self._pop2()
        self.push(left - right)

    def mul(self):
        right,left = self._pop2()
        self.push(left * right)

    def div(self):
        right,left = self._pop2()
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
    print("Good Calculator ")


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

    def push(self,value):
        print(f"PUSHING: {item}")
        super().push(value)

    def pop(self):
        item = super().pop()
        print(f"POPPED: {item}")
        return item


# An implementation of a "numeric" stack where items must be numbers
class NumericStack(Stack):

    def push(self,value):
        if not instance(value,(int,float)):
            raise TypeError("A number is required")
        super().push(value)


# Approach 1: Direct modification of internals


# Approach 2: Some kind of more cotrolled way of accomplishing the same thing.
