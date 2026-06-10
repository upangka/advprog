# Types Are Defined by Supported Operations

> Function has `annotations`

[type_define_by_support_opt.py](./code/type_define_by_support_opt.py)这里声明为Sequence类型，但是它不支持`__mul__`操作

![alt text](./attachments/type_support_opt.png)

# consistent-with

在Python中除了类型直接关系除了`subtype-of`父子类关系(**Nominal Typing**)之外，还有`consistent-with`一致性关系(**Ducking Typing**)。

[numic.py](./code/numic.py)

```python
# int consistent with complex
def real_imag(x: complex):
    print(x.real, x.imag)

>>> real_imag(1 + 2j)
1.0 2.0
>>> real_imag(1)
1 0
>>> print(issubclass(type(1), type(1 + 2j)))
False
```

# Type Hinting Generics In Standard Collections

[pep-0585](https://peps.python.org/pep-0585/)

| Collection                 | Type Hint Equivalent (deprecated) |
| -------------------------- | --------------------------------- |
| `tuple`                    | `typing.Tuple`                    |
| `list`                     | `typing.List`                     |
| `dict`                     | `typing.Dict`                     |
| `set`                      | `typing.Set`                      |
| `frozenset`                | `typing.FrozenSet`                |
| `collections.deque`        | `typing.Deque`                    |
| `collections.abc.Sequence` | `typing.Sequence`                 |
