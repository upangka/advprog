# Trick
 
## Fake Immutable

```python
>>> class Example:
...     def __setattr__(self,name,value):
...         raise RuntimeError("Can't set attribute")
...
...     def __init__(self,x):
...         self.x = x # will not work
...
>>> e = Example(3)  # RuntimeError
```

Aprroach One:

```python
>>> class Example:
...     def __setattr__(self,name,value):
...         raise RuntimeError("Can't set attribute")
...
...     def __init__(self,x):
...         self.__dict__['x'] = x
...
>>> e = Example(3)
>>> e.x
3
```

Approach Two:

```python
>>> class Example:
...     def __setattr__(self,name,value):
...         raise RuntimeError("Can't set attribute")
...
...     def __init__(self,x):
...         super().__setattr__('x',x)
...
>>> e = Example(3)
>>> e.x
3
```

deal with @dataclass(frozen=True)

```python
>>> from dataclasses import dataclass
>>> @dataclass(frozen=True)
... class Example:
...     x: int
...
>>>
>>> e = Example(3)
>>> e
Example(x=3)
>>> e.x = 3
Traceback (most recent call last):
  File "<python-input-39>", line 1, in <module>
    e.x = 3
    ^^^
  File "<string>", line 15, in __setattr__
dataclasses.FrozenInstanceError: cannot assign to field 'x'
>>> super(Example,e).__setattr__('x',666)
>>> e.x
666
```
