
# 背景

相比搜集所有结果或者解决方案才返回，不如得到一个解决方法或者结果就直接返回

```python
def find_solutions(apartment, domain):
    solutions = []
    for values in itertools.product(*domain.values()):
        try:
            candidates = dict(zip(domain.keys(), values))
            apartment(**candidates)
            # 先收集结果
            solutions.append(candidates) 
        except Fail:
            pass
    # 返回所有得到的结果
    return solutions


def main():
    solutions = find_solutions(apartment, domain)
    # 消费已经计算得到的结果
    for soln in solutions:
        print_apartment(**soln)
```

---

**生成器: 得到一个解决方法或者结果就直接返回**

```python
def find_solutions(apartment, domain):
    for values in itertools.product(*domain.values()):
        try:
            candidates = dict(zip(domain.keys(), values))
            apartment(**candidates)
            # 直接返回结果
            yield candidates
        except Fail:
            pass


def main():
    solutions = find_solutions(apartment, domain)
    # 得到一个结果，消费一个结果
    for soln in solutions:
        print_apartment(**soln)
```

# yield 生成器

[exercise_01.py](./code/exercise_01.py)

```python
def countdown(n):
    print(f'Count down from {n}')
    while n > 0:
        yield n
        n -= 1
```

for循环驱动

```python
>>> for x in countdown(3):
...     print('T-minus',x)
...
Count down from 3
T-minus 3
T-minus 2
T-minus 1
```

`next`驱动

```python
>>> c = countdown(3)
>>> c
<generator object countdown at 0x7fc7b3168450>
>>> next(c)
Count down from 3
3
>>> next(c)
2
>>> next(c)
1
>>> next(c)
Traceback (most recent call last):
  File "<python-input-9>", line 1, in <module>
    next(c)
    ~~~~^^^
StopIteration
```

`send`驱动 与`next`操作显示的结果一样

```python
>>> c = countdown(3)
>>> c.send(None)
Count down from 3
3
>>> c.send(None)
2
>>> c.send(None)
1
>>> c.send(None)
Traceback (most recent call last):
  File "<python-input-19>", line 1, in <module>
    c.send(None)
    ~~~~~~^^^^^^
StopIteration
```

# 生成器可以有return `value`

[exercise_01.py](./code/exercise_01.py)

```python
def func():
    yield 37
    return 42
```

```sh
>>> f = func()
>>> next(f)
37
>>> # 第二次调用执行return 此时生成器已经耗尽，抛出异常StopIteration
>>> # 并且这个异常携带者return的值
>>> next(f)
Traceback (most recent call last):
  File "<python-input-4>", line 1, in <module>
    next(f)
    ~~~~^^^
StopIteration: 42
```

通过`try`捕获异常

```sh
>>> f = func()
>>> next(f)
37
>>> try:
...     next(f)
... except StopIteration as e:
...     print(e.args,e.value)
...
(42,) 42
```

| 特性          | `args`                                    | `value`                                   |
| :------------ | :---------------------------------------- | :---------------------------------------- |
| **所属类**    | `BaseException` (所有异常父类)            | `StopIteration` 子类特有                  |
| **数据类型**  | 元组 (`tuple`)                            | 任意类型 (你传入的参数值)                 |
| **存储内容**  | 所有位置参数的完整列表                    | 专门存储“生成器返回值”                    |
| **是否总是存在** | 总是存在 (至少是空元组 `()`)            | 仅 `StopIteration` 有                     |
| **典型用途**  | 通用错误信息、调试                        | 被 `yield from` 或生成器协程接收          |

```python
class StopIteration(Exception):
    value: Any
```

# for...break只消费生成器部分内容

由于for循环的break,生成器的部分内容只被消费了，代表这个生成器永远不会运行完成。如果生成器函数执行某种清楚操作很重要，那么就不会得到执行。`finally`**里的代码将在python回收这个生成器的时候执行**

[exercise_02.py](./code/exercise_02.py)

```python
def countdown_bug(n):
    """如果有清理操作，验证不会执行"""
    print(f"Count down from {n}")

    while n > 0:
        yield n
        n -= 1
    print("Do some clean work")


def countdown(n):
    print(f"Count down from {n}")

    try:
        while n > 0:
            yield n
            n -= 1
    except Exception:
        # 只是消耗部分生成器，不代表异常
        print("Run here???")
    finally:
        # do some clear up operation
        print(f"Only made it to {n}")
```

```sh
>>> c = countdown_bug(3)
>>> # 生成器只有部分被消耗，break发生的时候，
>>> # clean并没有被执行
>>> for x in c:
...     if x == 2:
...         break
...     print(x)
...
Count down from 3
3
>>> # 继续消耗直到生成器结束
>>> sum(c)
Do some clearn work
1

>>> for x in countdown(3):
...     print(x)
...
Count down from 3
3
2
1
Only made it to 0
>>>
>>> # break 只消耗迭代器部分
>>> for x in countdown(3):
...     if x == 2:
...         break
...     print(x)
...
Count down from 3
3
Only made it to 2
```

同样的，上下文管理器也能保证生成器的清理操作被执行

```python
def func(filename):
    with open(filename) as f:
        ...
        yield from f
        ...
```


# 设计可重启的生成器

相当于用生成器实现迭代器

[exercise_03.py](./code/exercise_03.py)

```python
# 有意思的是这里我们将类命名为小写就像函数一样
# 在用户看来好像和之前的接口一样
class countdown:
    def __init__(self,n):
        self.n = n

    def __iter__(self):
        # copy every time
        n = self.n
        while n > 0:
            yield n
            n -= 1
```

```sh
>>> c = countdown(3)
>>> # for 会获得迭代器，相当于每次都
>>> # 调用了__iter__
>>> for x in c:
...     print(x)
...
3
2
1
>>> for x in c:
...     print(x)
...
3
2
1
```

# yield from 生成器委托

可以将`yield from iterable`看成`for i in iterable: yield i`

```python
def countup(stop):
    n = 1
    while n <= stop:
        yield n
        n += 1

def countdown(start):
    n = start
    while n > 0:
        yield n
        n -= 1


def up_and_down_v1(n):
    for x in countup(n):
        yield x
    for x in countdown(n):
        yield x

def up_and_down_v2(n):
    """相当于语法糖，简化写法"""
    yield from countup(n)
    yield from countdown(n)
```

运行效果是一样的
```sh
>>> for x in up_and_down_v1(3):
...     print(x, end=" ")
...
1 2 3 3 2 1 
>>> for x in up_and_down_v2(3):
...     print(x, end=" ")
...
1 2 3 3 2 1 
```

# 生成器实践

## 打平嵌套列表

[exercise_05.py](./code/exercise_05.py)

```sh
[1, 2, [3, [4, 5], 6, 7], 8] 
===>>>
1 2 3 4 5 6 7 8
```

```python
def flatten_v1(items):
    """递归的方式"""
    for i in items:
        if isinstance(i, list):
            yield from flatten_v1(i)
        else:
            yield i


def flatten_v2(items):
    """使用stack"""
    from typing import Iterable

    # 该栈存储的是迭代器
    stack = [iter(items)]
    while stack:
        try:
            item = next(stack[-1])
            if isinstance(item, Iterable):
                stack.append(iter(item))
            else:
                yield item
        except StopIteration:
            stack.pop()


def test(f, data=[1, 2, [3, [4, 5], 6, 7], 8]):
    for i in f(data):
        print(i, end=" ")
    print()


if __name__ == "__main__":
    test(flatten_v1)
    test(flatten_v2)
```

## 处理管道和工作流的数据

**处理多层嵌套控制流**

这是多层嵌套控制流[exercise_06.py](./code/exercise_06.py)

```python
# 单行注释中有 spam
# 干扰选项

import re
from pathlib import Path

for path in Path(".").rglob("*.py"):
    if path.exists():
        with path.open("rt", encoding="utf-8") as f:
            for line in f:
                m = re.match(".*(#.*)$", line)
                if m:
                    comment = m.group(1)
                    if "spam" in comment:
                        print(comment)  # spam在代码一行的后面
```

接下来进行优化，使用yield进行`打平代码`, [exercise_07.py](./code/exercise_07.py)

```python
import re
from pathlib import Path


def get_paths(topdir, pattern):
    for path in Path(topdir).rglob(pattern):
        if path.exists():
            yield path


def get_files(paths):
    for path in paths:
        with path.open("rt", encoding="utf-8") as f:
            yield f


def get_lines(files):
    for file in files:
        # 一个文件有多行，这里使用yield from委托的机制
        # yield from Iterable 也是可以的
        yield from file


def get_comments(lines):
    for line in lines:
        m = re.match(".*(#.*)$", line)
        if m:
            yield m.group(1)


def print_matching(comments, substring):
    for comment in comments:
        if substring in comment:
            print(comment)


if __name__ == "__main__":
    """这样的打平看起来就非常舒服"""
    paths = get_paths(".", "*.py")
    files = get_files(paths)
    lines = get_lines(files)
    comments = get_comments(lines)
    print_matching(comments, "spam")
```


### 正则解释

```python
re.match('.*(#.*)$',line)

行内容：print("hello")  # 这是一个注释
         └─.*─┘ └─(#.*)─┘
         匹配   捕获这个部分
                (注释)
```

| 符号 | 含义 | 在这段代码中的作用 |
| :--- | :--- | :--- |
| `.` | 任意字符（除换行符外） | 匹配行开头到 `#` 之间的任意字符 |
| `*` | 前面的字符出现 0 次或多次 | 配合 `.` 使用，匹配 `#` 前的内容（可以是0个或多个字符） |
| `()` | 分组捕获 | 将注释内容（`#` 及其后面的文字）捕获为一个分组 |
| `#` | 匹配字面意义的井号 | 匹配注释符号 `#` 本身 |
| `.*` | 任意字符出现 0 次或多次 | 匹配 `#` 后面的所有字符（直到行尾） |
| `$` | 匹配行尾 | 确保 `#.*` 一直到行尾才结束 |

---

| 组成部分 | 含义 |
| :--- | :--- |
| `.*` | 匹配 `#` 前面的任意内容（可以是空） |
| `(#.*)` | 捕获组：从 `#` 开始到行尾的所有内容 |
| `$` | 锚定行尾，确保匹配到行末 |

---

- `m.group(0)` = 整个匹配的文本（整行）
- `m.group(1)` = 第一个括号捕获的内容（即注释部分 `# 这是一个注释`）
- `m.group(2)` = 第二个括号捕获的内容（如果有）