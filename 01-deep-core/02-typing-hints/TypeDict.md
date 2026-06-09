# 类型提示

```python
from typing import TypeAlias
Person: TypeAlias = dict[str, str | int]
bob: Person = {"name": "Pkmer", "age": 25}
```

优点: 这样定义能够限制`值的类型提示`

![01_typed_alias](./attachments/01_typed_alias.png)

**缺点**： 但是这种情况`不能限制key的类型提示`，因为key现在可以是任何形式的字符串，比如将`name`改为`nameeee`。此时`TypedDict`就派上用场了

# TypedDict限制key的类型提示

```python
from typing import TypedDict
class Person(TypedDict):
    name: str
    age: int

bob: Person = {"nameee": "Bob", "age": 25, "city": "ShenZhen, China"}
```

可以看到现在`TypedDict`限制了key的类型提示，`nameee`和`city`都不属于`Person`的属性。

![](./attachments/02_typed_dict.png)

# `total`传给元类的关键字参数

1. 默认total为True，表示必须包含所有属性
2. 设置total为False，表示可以不包含所有属性

```python
from typing import TypedDict
class Person(TypedDict, total=True):
    name: str
    age: int
    city: str

>>> Person.__total__
True
```

![](./attachments/03_total.png)

> 元类相关知识
>
> `total=True`是传给元类的关键字参数，不是继承

```python
# 实际上Python的解释器
Person = TypedDict.__class__(    # TypedDict 的元类
    "Person",
    (TypedDict,),                # 父类元组
    {"name": str, "age": int},   # 类体命名空间
    total=True                   # ← 关键字参数
)
```

# 参考

- [Python官网：typing.TypedDict](https://docs.python.org/3.13/library/typing.html#typing.TypedDict)
- [Youtube: TypedDict is Awesome in Python](https://www.youtube.com/watch?v=RItoKMONirE)
