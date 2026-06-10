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

# `TypeAlias`

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

# 参数泛型与`TypeVar`

> Parameterized Generics and TypeVars

定义一个类型变量

```python
from typing import TypeVar
# T is a type variable
# that will be bound to a specific type with each usage.
T = TypeVar('T')
```

[04_param_generic.py](./code/04_param_generic.py)

![](./attachments/04_type_var.png)

使得参数的类型能够被映射到返回值类型上

## Restricted TypeVar

> 限制类型参数

```python
# T = TypeVar('T',int,float,str)
T = TypeVar('T',int,float)
```

此时严格限制类型，没有声明`str`

![](./attachments/05_restrict_typevar.png)

## Bounded TypeVar

1. `bound=Hashable` 设置了一个上界，意思是：HashableT 这个类型变量，只能被满足 Hashable 协议的类或它的子类所替代。Hashable 在这里是"你能在这个类型变量上安全调用的最大公约数"——类型检查器保证你至少能对它做哈希操作。
2. 记住传入的具体类型，并在返回值里复现它。返回类型和输入类型绑定了——传 list[int] 返回 int，传 list[str] 返回 str。类型检查器不需要你手动转型，就能精确推断出返回值的类型。

对应Java的写法就是`<T extends Hashable> T mode(List<T> data)`

```python
from typing import TypeVar
from collections import Counter
from collections.abc import Iterable, Hashable

# 等价Java <T extends Hashable> T mode(List<T> data)
HashableT = TypeVar('HashableT', bound=Hashable)

def mode(data: Iterable[HashableT]) -> HashableT:
    pairs = Counter(data).most_common(1)
    if not pairs:
        raise ValueError('no mode for empty data')
    return pairs[0][0]
```

```python
# tuple is hashable
# As long as the inferred type is consistent with the boundary
a = mode([i for i in range(10)])
```

只要输入的类型与（consistent with the boundary）一致，那么就可以返回这个类型
![](./attachments/06_bound_right.png)

```python
# ❌ list 是不可哈希的，类型检查器会报错
b = mode([[1, 2], [3, 4], [1, 2]])
```

不满足上界的条件
![](./attachments/06_bound_error.png)

# 参考

- 《Fluent Python: Chapter 8: Type Hints in Functions》
- [Python Doc: aliases](https://typing.python.org/en/latest/spec/aliases.html)
