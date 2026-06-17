

# `__match_args`

来自[interface](../07-high-order-functions/interface.md#exercise-6---the-split)

Add a `__match_args__` attribute to the `Result` class to support `match...case...` expression

```python
class Result:
    __match_args__ = ("_value",)

    def __init__(self, value):
        self._value = value

    def unwrap(self):
        raise NotImplementedError()


class Ok(Result):
    def unwrap(self):
        return self._value


class Error(Result):
    def unwrap(self):
        raise self._value
```

下面的两种使用：
1. 映射具体值
2. 映射具体类型`Error(SomeType())`

```python
def g(delay, value):
    match after(delay, lambda: math.sqrt(value)):
        case Ok(value): # 支持
            print("It worked:", value)
        case Error(exc):
            print("It failed:", exc)
```

```python
def h(delay, value):
    match after(delay, lambda: math.sqrt(value)):
        case Ok(value):
            print("It worked:", value)
        case Error(AfterError()) as e:  # 注意这行
            print(f"{e._value!r}")
        case Error(TypeError()):
            print("It failed: type error")
        case Error(ValueError()):
            print("It failed: bad value")
        case Error(e):
            raise e
```