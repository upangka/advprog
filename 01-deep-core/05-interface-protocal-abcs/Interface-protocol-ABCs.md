对象协议(object protocol)说白了就是一套方法清单：你想让对象扮演某个角色，它就得提供这些方法

# abc

`abc` 是"怎么造 ABC"的工具，`collections.abc` 是"已经造好的 ABC"。你写框架时用 `abc` 定义自己的抽象基类，做类型检查时用 `collections.abc` 引用现成的类型标准。

1. 这个模块`collections.abc`里存放的是 Python 官方已经定义好的、各种容器类型的抽象基类，比如 Sequence、MutableMapping、Iterable、Set、Callable 等。这些 ABC 都使用 abc 模块的机制来构建，但它们本身是一套"类型分类标签"。
2. 这个模块`abc`用来创建抽象基类的底层工具模块。它提供了 `ABC` 基类和 `@abstractmethod` 装饰器

## virtual subclasss

> virtual subclass 的核心作用：**让一个类在不继承某个 ABC 的情况下，被 isinstance 和 issubclass 认可为该 ABC 的子类**。

假设你写了一个框架，定义了一个抽象基类 Tombola。现在你发现标准库或第三方库里的某个类（比如 TombolaList）在行为上完全满足 Tombola 的接口——它有 pick()、load() 这些方法——但它的作者根本没听说过你的 Tombola，自然也不可能写 class TombolaList(Tombola) 来继承你。

这时候你有两个选择：

强迫所有用户在使用 TombolaList 时写一个包装类，手动继承 Tombola 并转发所有方法——繁琐。

直接告诉 Python："虽然 TombolaList 没有继承 Tombola，但它行为上完全满足要求，请把它当成 Tombola 的子类来对待。"

# Protocol设计规范

协议通常只定义单个方法，很少超过两三个。

## 资料

`__subclasshook__` 是 ABCMeta 元类提供的一个钩子方法，它在 isinstance(obj, SomeABC) 或 issubclass(SomeClass, SomeABC) 被调用时自动触发。它的核心作用是：让一个类在完全不继承 ABC 的情况下，也能被 isinstance 和 issubclass 认可为该 ABC 的子类型。 这是鸭子类型在 ABC 层面的实现入口。

```python
class Iterable(metaclass=ABCMeta):

    __slots__ = ()

    @abstractmethod
    def __iter__(self):
        while False:
            yield None
    # 处理鸭子类型
    @classmethod
    def __subclasshook__(cls, C):
        if cls is Iterable:
            return _check_methods(C, "__iter__")
        return NotImplemented

    __class_getitem__ = classmethod(GenericAlias)
```

实验

```
>>> class A:
...     def __iter__(self):
...         ...
...
>>> from collections.abc import Iterable
>>> issubclass(A,Iterable)
True
```
