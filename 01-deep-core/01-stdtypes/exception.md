# 重新抛出异常

当except捕获异常后，可以有多种方式重新抛出异常,其中涉及到**异常链**

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
```

输出
```python
It's Failed. Reason:  invalid literal for int() with base 10: 'N/A'
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

## raise X from None 抛出全新的异常

不包含其他异常链

[exercise_04.py](./code/exception/exercise_04.py)

```python
def spam():
    try:
        do_something()
    except ValueError as err:
        # raise from 将None封装到ApplicationError的__cause__属性
        # 但是注意仍然能够通过__context__属性访问到原始异常
        raise ApplicationError(1, "It failed") from None
        # raise ApplicationError(1,"It failed")


def main():
    try:
        spam()
    except ApplicationError as err:
        print("It's Failed. Reason: ", err.__cause__)
        print("__context__",type(err.__context__),err.__context__)
        print("__suppress_context__",err.__suppress_context__)
```

输出
```sh
It's Failed. Reason:  None
__context__ <class 'ValueError'> invalid literal for int() with base 10: 'N/A'
__suppress_context__ True
```

`__suppress_context__` 的核心作用就是：告诉 Python 在打印异常回溯信息（Traceback）时，不要显示 `__context__` 中的原始异常

这也是,下面两种方式的区别，在[异常回溯](#异常回溯traceback)表现的特别明显
```python
raise ApplicationError(1, "It failed") from None # __suppress_context 为 True
raise ApplicationError(1,"It failed") # __suppress_context__ 为False
```

## 异常回溯traceback

[exercise_05.py](./code/exception/exercise_05.py)
```python
def spam():
    try:
        do_something()
    except ValueError as err:
        # raise from 将None封装到ApplicationError的__cause__属性
        # 但是注意仍然能够通过__context__属性访问到原始异常
        raise ApplicationError(1, "It failed") from None
        # raise ApplicationError(1,"It failed")


def main():
    import traceback

    try:
        spam()
    except ApplicationError as err:
        tblines = traceback.format_exception(type(err), err, err.__traceback__)
        for i, v in enumerate(tblines, 1):
            print(i, "=>", v)
```

`raise ApplicationError(1, "It failed") from None`输出，没有输出context的信息

```sh
1 => Traceback (most recent call last):

2 =>   File "/home/pkmer/projects/advprog/01-deep-core/01-stdtypes/code/exception/exercise_05.py", line 25, in main
    spam()
    ~~~~^^

3 =>   File "/home/pkmer/projects/advprog/01-deep-core/01-stdtypes/code/exception/exercise_05.py", line 18, in spam
    raise ApplicationError(1, "It failed") from None

4 => ApplicationError: (1, 'It failed')
```

`raise ApplicationError(1, "It failed")`输出，输出了包含context的信息

```python
1 => Traceback (most recent call last):

2 =>   File "/home/pkmer/projects/advprog/01-deep-core/01-stdtypes/code/exception/exercise_05.py", line 14, in spam
    do_something()
    ~~~~~~~~~~~~^^

3 =>   File "/home/pkmer/projects/advprog/01-deep-core/01-stdtypes/code/exception/exercise_05.py", line 9, in do_something
    x = int("N/A")

4 => ValueError: invalid literal for int() with base 10: 'N/A'

5 =>
During handling of the above exception, another exception occurred:


6 => Traceback (most recent call last):

7 =>   File "/home/pkmer/projects/advprog/01-deep-core/01-stdtypes/code/exception/exercise_05.py", line 26, in main
    spam()
    ~~~~^^

8 =>   File "/home/pkmer/projects/advprog/01-deep-core/01-stdtypes/code/exception/exercise_05.py", line 19, in spam
    raise ApplicationError(1,"It failed")

9 => ApplicationError: (1, 'It failed')
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