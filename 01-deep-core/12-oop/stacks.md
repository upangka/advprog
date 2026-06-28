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
class Stack: ... # You define

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



# Exercise 2 - A Calculator

Use your stack class to make a simple 4-function calculator. You need to support four operations(`add`,`sub`,`mul`,`div`) in addition to push/pop. For exmaple:

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