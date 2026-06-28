# Introduction


In an unusual shift in business strategy, management has decided
that a full effort will be made to recreate the greatest handheld
computer ever created--the HP 35 calculator (only sleeker and with
BlueTooth).  HP Calculators are somewhat infamous for their use
of [RPN](https://en.wikipedia.org/wiki/Reverse_Polish_notation)
wherein to perform a calculation such as "3 + 4", you would first
enter 3, enter 4, and then hit the `+` key last.  

Underneath the hood,these calculators are based on a stack structure. The entered values get saved on a stack. Operations such as `+` and `*` consume he top two items, carry out the calculation, and save the result back on the stack.

Because stacks seem central to the design, you've been tasked with problem of making stacks. How hard could it be?

# Exercise 1 - The stack

Define a Stack data structure. It should support `push()` and `pop()` operations, that work as follows:

```python
>>> s = Stack()
>>> s.push(23)
>>> s.push(45)
>>> len(s)
2
>>> a = s.pop()   # Returns the last item pushed
>>> a
45
>>> b = s.pop()
>>> b
23
>>> len(s)
0
>>> s.push(a + b)   # Push a result onto the stack
>>> s.pop()
68
```

```python
class Stack: # You define here
    
    # Must have
    # 相当于统一操作接口了，至于内部的实现细节，随便你怎么实现
    def push(self,item): 
        ...
    def pop(self): 
        ...
    def __len__(self): 
        ...


def test_stack(s):
    s.push(23)
    s.push(45)
    assert len(s) == 2
    assert s.pop() == 45
    assert s.pop() == 23
    assert len(s) == 0
    print("Good stack!")
```

[exercise_01.py](./code/stacks/exercise_01.py)

```python
class Stack:
    def __init__(self, *, container=None):
        self._items = container if container else []

    def pop(self):
        return self._items.pop()

    def push(self, item):
        self._items.append(item)

    def __len__(self):
        return len(self._items)


def test_stack(s):
    s.push(23)
    s.push(45)
    assert len(s) == 2
    assert s.pop() == 45
    assert s.pop() == 23
    assert len(s) == 0
    print("Good stack!")


if __name__ == "__main__":
    test_stack(Stack())
    from array import array

    test_stack(Stack(container=array("i")))
```


# Exercise 2 - A Calculator

Use your stack class to make a simple 4-function calculator. You need to support four operations(`add`,`sub`,`mul`,`div`) in addition to push/pop. For exmaple:

```python
class Calculator:
    def push(self): 
        ...
    def pop(self): 
        ...
    def add(self): 
        ...
    def sub(self): 
        ...
    def mul(self): 
        ...
    def div(self): 
        ...
```

```sh
>>> s.push(23)
>>> s.push(45)
>>> s.add()
>>> s.pop()
68
```

All math operations consume the top two items on the stack and replace them with the result. Here's how you would calculate `2*(3+4)`

```sh
>>> s.push(2)
>>> s.push(3)
>>> s.push(4)
>>> s.add()
>>> s.mul()
>>> s.pop()
14
```

[exercise_02.py](./code/stacks/exercise_02.py)

```python
class Calculator:

    def __init__(self):
        self._stack = Stack(container=[])

    def push(self,item):
        self._stack.push(item)
        
    def pop(self):
        return self._stack.pop()

    def add(self):
        r = self.pop() + self.pop()
        self.push(r)
        return r

    def sub(self):
        b = self.pop()
        a = self.pop()
        r = a - b
        self.push(r)
        return r

    def mul(self):
        r = self.pop() * self.pop()
        self.push(r)
        return r

    def div(self):
        b = self.pop()
        a = self.pop()
        r = a / b
        self.push(r)
        return r
```

使用operator模块优化一下：

```python
import operator

class Calculator:

    def __init__(self) -> None:
        self._stack = Stack(container=[])

    def _do_cal(self, op):
        right = self.pop()
        left = self.pop()
        r = op(left, right)
        self._stack.push(r)
        return r

    def push(self,item):
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
```

# Exercise 3 - The Mutable

A central idea of object-oriented programming is that it is often focused on behavior and mutation. You create an object. You execute methods on the object. Those methods tend to modify the state of the object.

However, what happens when a method fails? Consider the following test involving a calculator.

本次练习的目标是，当对象的行为发生错误的时候，对象本身的内部状态保持不变

```python
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
    calc.add()               # Does this work?
    assert calc.pop() == 68  # Does this work?
```

Your Task: Modify the calculator class so that its method either work entirely or fail entirely. **Methods that fail should leave the calculator state unchanged**.


原子性操作。保持状态的一致性:
1. 操作成功(如: add) → 所有修改生效
2. 操作失败(如: add) → 回滚到操作前的状态，没有任何残留变化(恢复之前的数值23)


[exercise_03.py](./code/stacks/exercise_03.py)

```python
class NotEnoughValues(Exception):
    pass

class Calculator:
    def __init__(self) -> None:
        self._stack = Stack(container=[])

    def _do_cal(self, op):
        if len(self._stack) < 2:
            raise NotEnoughValues(f"Not enough elements to support {op.__name__}")
        right = self.pop()
        left = self.pop()
        r = op(left, right)
        self._stack.push(r)
        return r
    
    # 其他保持不变
    ...
```

**DISCUSSION**: What are the pros and cons of this design?
- pros and cons /proʊz ænd kɑːnz/ 优缺点 pro = 优点（正面），con = 缺点（负面），固定搭配


# Exercise 4 - The Debugged and the Defended

Perter is working on some code that involves the calculator class. However, it's broken and he's trying to figure out why. To help dedug it,he'e written a customized `Stack` class with some print statements added to it.

Similarly, Arjoon has decided that the calculator should do a better job of type-checking. "Why is this allowed?" he asks:

```python
>>> s = Stack()
>>> s.push("hello")
>>> s.push(4)
>>> s.mul()
>>> s.pop()
'hellohellohellohello'
```

To address this, he's created a custom Stack with some type-checking added to it.

Although Peter and Arjoon, have created custom Stack classes, they're now both perplexed about how to use them with the Caculator class.How would you modify the Calculator class to allow alternative Stack implementations to be used?

- perplexed /pərˈplekst/ 困惑的、迷惑不解的、不知所措的 指因事情复杂或难以理解而感到困惑和不确定，不知道该怎么办。


```python
# An implementation of a Stack with debugging
class DebugStack(Stack):
    def push(self,item):
        print("PUSHING:",item)
        super().push(item)
        
    def pop(self):
        item = super().pop()
        print("POPPED:",item)
        return item
    
class NumericStack(Stack):
    def push(self,item):
        if not isinstance(item,(int,float)):
            raise TypeError("A number is required")
        super().push(item)
```

Verify that these Stacks pass the test.

```python
test_stack(DebugStack())
test_stack(NumericStack())
```

> **Note: This is a perfectly reasonable use of inheritance -- using it to create a modified stack.**

Figure out some way to use either one of these stacks with your calculator. Make sure you can run the `test_calculator` test and that it works without modification.

[exercise_04.py](./code/stacks/exercise_04.py)

```python
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
```


# Exercise 5 - The conflict

Both Peter and Arjoon have created alternative Stack implementations. However, a debate has now erupted about how to enable the functionality of *both* classes at the same time (that is, to have both type-checking and debugging turned on all at once).

There seems to be no obviously "great" way to use two stacks at once. However, Mary observes that both of these features could be implemented as an "add-on" instead.

To illustrate, she's written the following classes below. Your task: figure how theses classes are supposed to be used with either the Stack or Calculator class to enable debugging and type checking at the same time.

[exercise_05.py](./code/stacks/exercise_05.py)

```python
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
```

```sh
>>> calc = MyCalculator()
>>> calc.push('hello')
PUSHED: hello
Traceback (most recent call last):
  File "<python-input-3>", line 1, in <module>
    calc.push('hello')
    ~~~~~~~~~^^^^^^^^^
  File "/home/pkmer/projects/advprog/01-deep-core/12-oop/code/stacks/exercise_05.py", line 9, in push
    super().push(item)
    ~~~~~~~~~~~~^^^^^^
  File "/home/pkmer/projects/advprog/01-deep-core/12-oop/code/stacks/exercise_05.py", line 22, in push
    raise TypeError("Require a number")
TypeError: Require a number
>>> calc.push(1)
PUSHED: 1
>>> calc.pop()
POPPED: 1
1
```