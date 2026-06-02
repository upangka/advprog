Introduction

In an unusual shift in business strategy, management has decided
that a full effort will be made to recreate the greatest handheld
computer ever created--the HP 35 calculator (only sleeker and with
BlueTooth). HP Calculators are somewhat infamous for their use
of [RPN](https://en.wikipedia.org/wiki/Reverse_Polish_notation)
wherein to perform a calculation such as "3 + 4", you would first
enter 3, enter 4, and then hit the "+" key last. The underlying
architecture is based on a stack.

Thus, you've been tasked with the problem of making stacks. How
hard could it be?

# Patching

Just modified the Stack class directly. Metaclass programming

```python
>>> s = Stack()
>>> s.push(3)
>>> s.pop()
3
>>> add_stack_debug(Stack)  # Just modify the Stack class, the instance will be influenced
>>> s.push(3)
PUSHING: 3
>>> s.pop()
POPPED: 3
3
```

> One difference: decorator version may run faster (don't use super, which is kind of expensive)

```python
>>> @add_stack_debug        # Decorator version
... class MyStack1(Stack):
...     pass
...
>>>
>>> # Mixin version
>>> class MyStack2(DebugStackOps,Stack):
...     pass
...
```
