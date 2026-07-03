# 重新抛出异常

当except捕获异常后，可以有多种方式重新抛出异常

## 1. 原原本本的重新抛出异常

```python
try:
  file = open('foo.txt','rt')
except FileNotFoundError:
  print("Well, that didn't work")
  raise # 重新抛出当前异常
```

## 2. raise X from y X.`__cause__`包含之前的异常

[exercise_03.py](./code/exception/exercise_03.py)

```python
class ApplicationError(Exception):
    def __init__(self, errno, msg):
        self.args = (errno, msg)
        self.errno = errno
        self.msg = msg

def do_something():
    x = int("N/A")

def spam():
    try:
        do_something()
    except ValueError as err:
        # raise from 将err封装到ApplicationError的__cause__属性
        raise ApplicationError(1, "It failed") from err

def main():
    try:
        spam()
    except ApplicationError as err:
        print("It's Failed. Reason: ", err.__cause__)

if __name__ == "__main__":
    main()
```


交互的方式，如果是`raise X from y`，那么X的实例此时`__cause__`，属性就会有值，而不是None


```python
>>> # 普通的异常__cause__ = None
>>> a = ApplicationError(0, 'test')
>>> a.args
(0, 'test')
>>> a.__cause__
>>> a.__cause__ is None
True
>>> # raise from ...出来的异常，__cause__有值
>>> try:
...     spam()
... except ApplicationError as err:
...     print(type(err.__cause__))
...     print(err.__cause__)
...     print(err.__cause__.args)
...
<class 'ValueError'>
invalid literal for int() with base 10: 'N/A'
("invalid literal for int() with base 10: 'N/A'",)
```



# 异常层次

常见的异常根类与基础类

| 异常类型 | 描述 |
|---------|------|
| BaseException | 所有异常的根类 |
| Exception | 所有与程序相关的错误的基础类型 |
| LookupError | 所有与容器查找相关的错误的基础类型 |

常见的派生的子异常

| 异常类型 | 描述 |
|---------|------|
| RuntimeError | 通用的“something bad happened”错误，父类是Exception |
| TypeError | 应用错误类型对象的操作 |


# 自定义新的异常

## 异常pythonic命名

```python
# ✅ 推荐做法1：自定义异常继承自 Exception，用 Error 后缀（Python 风格）
class DatabaseConnectionError(Exception):
    pass

# ✅ 推荐做法2：如果你在写框架，用 Exception 后缀作为基类（框架风格）
class MyFrameworkException(Exception):
    pass

# ✅ 推荐做法3：非错误类控制流，用明确的后缀
class RetryAttemptsExceeded(Exception):
    """重试次数超限（逻辑控制，非错误）"""
    pass

# ❌ 不太推荐：用太模糊的名字
class MyError:  # 没有继承 Exception
    pass
```

## 异常的标准属性


### args 

`args`通常是字符元组在打印异常回溯信息时被使用
1. args 不是 Python 层面的字典属性，而是 C 层面的结构体字段
2. Exception.__new__ 只设置了 C 结构体字段（args），没有设置 Python 字典（__dict__）

```python
>>> from dataclasses import dataclass
>>> import inspect
>>> from typing import Any
>>> @dataclass
... class ApplicationError(Exception):
...     value: Any
...
>>> # 生成的__init__只包含value,不包含args
>>> inspect.signature(ApplicationError)
<Signature (value: Any) -> None>
>>> err = BaseException.__new__(ApplicationError,"hello","world")
>>> vars(err)
{}
>>> # 但是现在args确是有值的，不再dict中
>>> err.args
('hello', 'world')
>>> # 初始化我们的异常
>>> if isinstance(err,ApplicationError):
...     err.__init__("init something")
...
>>> err
ApplicationError(value='init something')
```

当自定义异常的时候，可以重写`__init__`方法,但是要给args属性赋值，不用`dataclass`

可以看到无论是使用`self.args`或者`super().__init__`的方式，vars都查看不到。
```python
>>> class ApplicationError(Exception):
...     def __init__(self,errno,msg):
...         # 使用父类的初始化
...         # super().__init__(errno,msg)
...         # 手动赋值
...         self.args = (errno,msg)
...         # 自定义的属性
...         self.errno = errno
...         self.msg = msg
...
>>> err = ApplicationError(1,"Not Responding")
>>> vars(err)
{'errno': 1, 'msg': 'Not Responding'}
>>> err.args
(1, 'Not Responding')
>>> raise err
```

```python
>>> class ApplicationError(Exception):
...     def __init__(self,errno,msg):
...         # 使用父类的初始化
...         super().__init__(errno,msg)
...         # 手动赋值
...         # self.args = (errno,msg)
...         # 自定义的属性
...         self.errno = errno
...         self.msg = msg
...
>>> err = ApplicationError(1,"Not Responding")
>>> vars(err)
{'errno': 1, 'msg': 'Not Responding'}
>>> err.args
(1, 'Not Responding')
```

原因解释

```python
# 检查 BaseException 的 args 属性是什么
print(BaseException.__dict__['args'])
# 输出：<attribute 'args' of 'BaseException' objects>
# 这是一个 C 实现的描述符！

# 这意味着：
# 1. 当你读取 err.args 时 → 调用 C 的 getter，从 C 结构体读取
# 2. 当你写入 err.args = (1, "Not Responding") 时 → 调用 C 的 setter，存入 C 结构体
# 3. 这个属性不会出现在 Python 的 __dict__ 中
```