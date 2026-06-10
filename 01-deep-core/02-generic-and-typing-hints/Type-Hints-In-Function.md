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

# TypeAlias

[Python Doc: aliases](https://typing.python.org/en/latest/spec/aliases.html)类型别名

[03_typealias.py](./code/03_typealias.py)

```python
FromTo = tuple[str,str]

# 显示声明TypeAlias in Python 3.10
from typing import TypeAlias
FromTo: TypeAlias = tuple[str,str]

# 使用type in python 3.12
type FromTo = tuple[str,str]
```

```python
from collections.abc import Iterable
from typing import TypeAlias

# type FromTo = tuple[str, str]
FromTo: TypeAlias = tuple[str, str]


def zip_replace(text: str, changes: Iterable[FromTo]) -> str:
    for from_, to in changes:
        text = text.replace(from_, to)
    return text


l33t = [("a", "4"), ("e", "3"), ("l", "1"), ("o", "0"), ("s", "5"), ("t", "7")]
print(zip_replace("hello world", l33t))
```

# 参考

- 《Fluent Python: Chapter 8: Type Hints in Functions》
- [Python Doc: aliases](https://typing.python.org/en/latest/spec/aliases.html)
