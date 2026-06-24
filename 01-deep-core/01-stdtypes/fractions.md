# Introduction

Peter needed to help his daughter Eva do her 5th grade math homework
on fractions. Remembering a bit of math, knowing that Python had
tuples, and recalling a bit of code from a dusty blue Wizard CS
textbook, he wrote the following functions and said "maybe these
can help you:"

[frac.py](./code/fractions/frac.py)

```python
def add_frac(a, b):
    return (a[0] * b[1] + a[1] * b[0], a[1] * b[1])

def sub_frac(a, b):
    return (a[0] * b[1] - a[1] * b[0], a[1] * b[1])

def mul_frac(a, b):
    return (a[0] * b[0], a[1] * b[1])
、
def div_frac(a, b):
    return (a[0] * b[1], a[1] * b[0])
```

In this code, fractions are stored as a tuple containing the
numerator and denominator. For example, the fraction 2/3 is
written as follows:

```python
>>> a = (2, 3)
>>>
```

To perform various mathematical operations, the above functions
are used. Here's a sample of how they work.


```python
a = (2, 3)
b = (3, 4)
assert add_frac(a, b) == (17, 12)
assert mul_frac(a, b) == (6, 12) 
>>> add_frac(a,b)
(17, 12)
>>> mul_frac(a,b)
(6, 12)  # 注意这里没有约分
```

As a refresher, here are the rules for arithmetic with fractions:

```sh
  n1   n2   n1*d2 + d1*n2
  -- + -- = -------------
  d1   d2       d1*d2

  n1   n2   n1*d2 - d1*n2
  -- - -- = -------------
  d1   d2       d1*d2

  n1   n2   n1*n2
  -- * -- = -----
  d1   d2   d1*d2

  n1   n2   n1*d2
  -- / -- = -----
  d1   d2   d1*n2
```

Take a look at the code, convince yourself that it probably works.

---

Exercises

We're going to work a series of 8 exercises, found in the files
ex1.py - ex8.py.  Each of them build upon the idea of implementing
Fractions, but in different ways and with different aspects of design.
Be aware, you may have to copy/paste code between exercises.  They
also get progressively more advanced as you go.  Expect discussion
as we work on it.


# Exercise 1 - The requirements

"Dad, I used the code in frac.py and I lost all sorts of points. For
example, I produced one answer of (6, 12). The teacher wanted (1, 2)
instead. Can you change the code to put answers in lowest terms?"

"Also, the teacher told us to never put a negative number in the
denominator. So, you'd never write (2, -3). Instead you'd write
(-2, 3). Also, (-2, -3) should just be written as (2, 3)."

"And last, but not least, what is with that code you wrote? I can
hardly read anything that's going on in there with all of that tuple
indexing."

To fix all of these problems, you decide to introduce a few helper
functions. A make_frac() function will be used to construct the
tuples and put things in lowest terms. To hide tuple indexing,
you'll use numerator() and denominator() functions.

- numerator /ˈnuː.mə.reɪ.t̬ɚ/ n. 分子
- denominator /dɪˈnɑː.mə.neɪ.t̬ɚ/ n. 分母（分数中位于分数线下方的数字，表示将整体分成的总份数


欧几里得算法的核心公式是：

`gcd(a, b) = gcd(b, a % b)`

```python
def gcd(a, b):
    # Greatest common divisor
    # a, b = abs(a), abs(b) 需要符号
    while b:
        a, b = b, a % b
    return a
```

不断把 `(a, b)` 替换为 `(b, a % b)`，直到余数为 0，此时 `a` 就是最大公约数。

| 步骤 | 示例 |
|------|------|
| 1 | `gcd(12, 18) = gcd(18, 12)` |
| 2 | `gcd(18, 12) = gcd(12, 6)` |
| 3 | `gcd(12, 6) = gcd(6, 0)` |
| 4 | `gcd(6, 0) = 6` |



```python
def make_frac(numer, denom):
    d = gcd(numer, denom)
    return (numer // d, denom // d)

def numerator(f):
    return f[0]

def denominator(f):
    return f[1]
```

Your task. Rewrite the `add_frac()`, `sub_frac()`, `mul_frac()`, and
`div_frac()` functions to ONLY use the above functions. Then, make
sure the unit tests below pass.

```python
def add_frac(a, b):
    pass

def sub_frac(a, b):
    pass

def mul_frac(a, b):
    pass

def div_frac(a, b):
    pass

def test_frac():
    a = make_frac(4, 6)
    assert (numerator(a), denominator(a)) == (2, 3)

    b = make_frac(-3, -4)
    assert (numerator(b), denominator(b)) == (3, 4)

    c = make_frac(3, -4)
    assert (numerator(c), denominator(c)) == (-3, 4)

    d = add_frac(a, b)
    assert (numerator(d), denominator(d)) == (17, 12)

    ...
```

[exercise_01.py](./code/fractions/exercise_01.py) 可读性一下就增强了很多
```python
def add_frac(a, b):
    return make_frac(
        numerator(a) * denominator(b) + denominator(a) * numerator(b),
        denominator(a) * denominator(b),
    )

def sub_frac(a, b):
    return make_frac(
        numerator(a) * denominator(b) - denominator(a) * numerator(b),
        denominator(a) * denominator(b),
    )

def mul_frac(a, b):
    return make_frac(numerator(a) * numerator(b), denominator(a) * denominator(b))

def div_frac(a, b):
    return make_frac(numerator(a) * denominator(b), denominator(a) * numerator(b))
```

# Exercise 2 

"Dad? Tuples? Accessor functions? Really? What is this?"

Grumbling, Peter starts thinking about the general design problem of
**data abstraction**. Despite his use of tuples, the functionality of
his code is still fairly well organized into layers. For example,
none of the core math functions (add_frac, sub_frac, mul_frac, etc.)
know anything about tuples. Instead, they use the **accessor
functions** such as `numerator(r)` and `denominator(r)`. Fractions are
always constructed using `make_frac()`. Abstraction is good.

"I'll show her!"

Peter decides that he can easily change his code to use dictionaries
without breaking anything else. All he needs to do is change the
`make_frac()`, `numerator()`, and `denominator()` functions. Nothing else
needs to change, including the tests.

- grumbling /ˈɡrʌm.bəl.ɪŋ/ v. 咕哝；抱怨；嘟囔（指低声表达不满或牢骚，通常不是大声抗议，而是带着不满情绪的小声嘀咕。


[exercise_02.py](./code/fractions/exercise_02.py)

```python
def make_frac(numer, denom):
    d = gcd(numer, denom)
    return {"numerator": numer // d, "denominator": denom // d}


def numerator(f):
    return f["numerator"]


def denominator(f):
    return f["denominator"]
```


# Exercise 3

During your coffee break, you decide to show your fraction code to a
Lisp programmer at the office.

"You know, you could really shatter 5th grade minds if you
represented fractions entirely as a function. Here, something like
this:"

- shatter /ˈʃæt.ər/ v. 粉碎；摧毁；使震惊（指彻底击碎或破坏某物，在上下文中，"shatter 5th grade minds" 的意思是：摧毁五年级学生的认知/让他们彻底震惊。这里的语气是幽默夸张的，表示如果用函数来表示分数（而不是用元组或字典这种直观的数据结构），会完全颠覆小学生的理解方式。这是 Lisp 程序员在调侃函数式编程的抽象方式——用函数来表示数据（闭包）是一种极其高级但又非常“奇怪”的做法

```python
# 生产分数
def make_frac(numer, denom):
    d = gcd(numer, denom)
    numer = numer // d
    denom = denom // d
    def frac(s):
        return numer if s else denom
    return frac

# access funcs
def numerator(f):
    return f(True)

def denominator(f):
    return f(False)
```

What is this madness? Paste your implementation of `add_frac()`,
`sub_frac()`, `mul_frac()`, and `div_frac()` here. MAKE NO CHANGES.
Verify that it still passes all of the unit tests--somehow.

- madness /ˈmæd.nəs/ n. 疯狂；荒唐；荒谬的行为（指某种想法或做法极其奇怪、不合常理，让人难以理解或接受。在上下文中，“What is this madness?” 的意思是：这到底是什么疯狂的东西？ 这是 Peter 在看到 Lisp 程序员用函数来表示分数时的反应——因为这种用闭包来存储数据的方式完全违背了常规直觉，在传统的命令式编程思维看来简直是“疯了”。这个词在这里带有幽默和夸张的色彩，表达对函数式编程抽象方式的震惊和不理解）

[exercise_03.py](./code/fractions/exercise_03.py) 一模一样的复制，根本不做任何更改，尽管使用函数式的分数，但是仍然正常工作。

```python
def add_frac(a, b):
    return make_frac(
        numerator(a) * denominator(b) + denominator(a) * numerator(b),
        denominator(a) * denominator(b),
    )


def sub_frac(a, b):
    return make_frac(
        numerator(a) * denominator(b) - denominator(a) * numerator(b),
        denominator(a) * denominator(b),
    )


def mul_frac(a, b):
    return make_frac(numerator(a) * numerator(b), denominator(a) * denominator(b))


def div_frac(a, b):
    return make_frac(numerator(a) * denominator(b), denominator(a) * numerator(b))
```

As you collect the pieces of your brain, ponder the fact that those
top level functions `make_frac()`, `numerator()`, and `denominator()`
really saved you a lot of hassle here. Yes, **the underlying
representation changed into something else, but none of the
higher level code had to change**.

- ponder /ˈpɑːn.dər/ v. 沉思；仔细思考（指对某个问题进行认真、深入的思考，通常带有哲学性或探索性，不是简单的随便想想。
- hassle /ˈhæs.əl/ n. 麻烦；困难；争论（指令人不愉快或费时费力的麻烦事。在上下文中，"saved you a lot of hassle" 的意思是：帮你省去了很多麻烦。


# Exercise 4

"Dad, have you ever considered using a named tuple?"  For example:

```python
from typing import NamedTuple

class Fraction(NamedTuple):
    numerator : int
    denominator : int

def make_frac(numer, denom):
    ... # You define

def numerator(f):
    ... # You define

def denominator(f):
    ... # You define
```

[exercise_04.py](./code/fractions/exercise_04.py)

```python
def make_frac(numer, denom):
    d = gcd(numer, denom)
    return Fraction(numer // d, denom // d)


def numerator(f):
    return f.numerator


def denominator(f):
    return f.denominator
```

## Duck Type

Did you know that the math functions can now also work with normal 
integers if you implement the accessor functions so that they use
the dot (.) for attribute access? Try this:

```python
def numerator(a):
    return a.numerator

def denominator(a):
    return a.denominator
```
Verify that it works:

```python
>>> a = make_frac(2, 3)
>>> b = add_frac(a, 1)
>>> b
Fraction(numerator=5, denominator=3)
>>>
```

Can you explain why this works?

这其实是 Python 鸭子类型（Duck Typing） 的一个精妙应用。

`add_frac(a, 1)` 中的 1 是一个普通的 int 对象。在 Python 中，int 类型本身就有 `numerator` 和 `denominator` 属性吗？

让我们在 Python 中验证一下：

```python
>>> (1).numerator
1
>>> (1).denominator
1
```

在 Python 中，整数对象自带了 `numerator` 和 `denominator` 属性（因为 int 继承自 `numbers.Rational`），它们分别返回整数本身和 1

`1.numerator` 报错是因为解析器把 1. 误认为浮点数前缀，而不是整数加属性访问。加括号 `(1)` 或加空格 `1 .` 可以消除歧义。

```sh
>>> 1.numerator
  File "<stdin>", line 1
    1.numerator
>>> # 加上空格
>>> 1 .numerator
1
```


# Exercise 5

The function `make_frac()` is used to construct fractions. One feature
of `make_frac()` is that it puts a fraction number into lowest terms and
normalizes the sign to always appear in the numerator. For example:

```python
>>> a = make_frac(4, -6)
>>> a.numerator
-2
>>> a.denominator
3
>>>
```

How would you modify the Fraction namedtuple class to have the same
behavior if you used it's normal constructor?

```python
>>> a = Fraction(4, -6)
>>> a.numerator
-2
>>> a.denominator
3
>>>
```

Disclaimer: This is hard and not obvious. But, it points to deeper
problems. Maybe `NamedTuple` is not the solution we seek.

- Disclaimer /dɪsˈkleɪ.mər/ n. 免责声明；声明（指用来澄清意图、说明特定条件或排除责任的声明。在上下文中，"Disclaimer: This is hard and not obvious." 的意思是：声明：这很难且不显然。



```python
class Fraction(NamedTuple):
    numerator: int
    denominator: int

    # You'll need to make modifications to pass the test below. Logically,
    # you'll want to make it so the numerator/denominator are reduced to
    # lowest terms as you might have done in an __init__() method. Sadly,
    # doing that does NOT work (can you figure out why?)
    # DOES NOT WORK! Can you think of an alternative that achieves the same
    # effect?
    def __new__(cls, numerator, denominator):
        d = gcd(numerator, denominator)
        numerator = numerator // d
        denominator = denominator // d
        # AttributeError: Cannot overwrite NamedTuple attribute __new__
        return super().__new__(cls, numerator, denominator)

    def __init__(self, numerator, denominator):
        d = gcd(numerator, denominator)
        self.numerator = numerator // d  # Would not work ever
        self.denominator = denominator // d  # Would not work ever
```

因为 `NamedTuple` 的构造函数 `__new__` 在你传入参数时直接就把值存进去了，中间没有任何可以插入逻辑的地方。你没办法重写 `__init__` 或 `__new__` 来“拦截”参数并修改它们，因为 NamedTuple 在底层定义时，会生成一个不可修改的类，它的行为已经固定了

关于`__new__`

```python
>>> class Point:
...     def __init__(self,x,y):
...         self.x = x
...         self.y = y
>>> # 创建实例，但是并没有初始化
>>> p = Point.__new__(Point)
>>> p = Point.__new__(Point,2,3)
>>> p.__dict__
{}
>>> p.__init__(2,3)
>>> p.__dict__
{'x': 2, 'y': 3}
```

解决方案[exercise_05.py](./code/fractions/exercise_05.py)

```python
class _Fraction(NamedTuple):
    numerator: int
    denominator: int


class Fraction(_Fraction):
    def __new__(cls, numerator, denominator):
        d = gcd(numerator, denominator)
        numerator = numerator // d
        denominator = denominator // d
        return super().__new__(cls, numerator, denominator)
```

# Exercise 6

Modeling fractions as a data structure with a collection of standalone functions isn't very "Pythonic." Python has a protocol for manipulating numbers via operators such as `+`, `-`, `*`, and `/`. These operators are mapped to methods such as `__add__()` and `__mul__()`.

Pythonic 是 Python 社区里的一个核心概念，指“符合 Python 语言设计哲学和惯用风格的代码”。

Pythonic 代码的特点是：

1. 简洁、优雅、易读
2. 充分利用 Python 的语言特性（如魔法方法、鸭子类型、上下文管理器等）
3. 让代码看起来“自然”，而不是把其他语言的风格硬搬到 Python 里

| 当前写法（不够 Pythonic） | Pythonic 写法 |
|---------------------------|---------------|
| `add_frac(a, b)`          | `a + b`       |
| `sub_frac(a, b)`          | `a - b`       |
| `mul_frac(a, b)`          | `a * b`       |
| `div_frac(a, b)`          | `a / b`       |

In this exercise, we're going to write a Fraction class that works like a proper Python number. To do this, you'll need to implement a variety of so-called "magic" methods such as __add__, __sub__, __mul__, etc. Some later stages of the exercise have you make it a bit nicer to work with by implementing a few other methods.

However, as a twist of fate, you are going to be required to continue supporting the "old" programming interface. As often is the case in a project, you've to support both old and new code at the same time. So, we've got to think about that.

- twist /twɪst/ n. 转折；扭曲；意外变化（指事物发展方向的突然改变，带有出乎意料的意味）
- fate /feɪt/ n. 命运；天数（指注定的、无法改变的发展方向或结局）
- as a twist of fate = 偏偏命运就是这样安排的，用来引出一种意想不到的、带有戏剧性的局面。

There are various test functions in this file that need to pass. Read ahead and comment them out as you work.

```python
def gcd(a, b):
    # Greatest common divisor
    while b:
        a, b = b, a % b
    return a

# We will define a proper class
class Fraction:
    def __init__(self, numerator, denominator):
        d = gcd(numerator, denominator)
        self.numerator = numerator // d
        self.denominator = denominator // d

    # Define various magic methods for Python operators
    def __add__(self, other):
        ...

    def __sub__(self, other):
        ...

    ...
```

Legacy interface. We'll continue to support it for backwards compatibility

```python
def make_frac(numerator, denominator):
    return Fraction(numerator, denominator)
```

Note: The following are details about redefining Python magic methods.

| 运算符/函数 | 对应的魔法方法 |
|-------------|----------------|
| `a + b`     | `a.__add__(b)` |
| `a - b`     | `a.__sub__(b)` |
| `b + a`     | `a.__radd__(b)` |
| `b - a`     | `a.__rsub__(b)` |
| `a * b`     | `a.__mul__(b)` |
| `a / b`     | `a.__truediv__(b)` |
| `a == b`    | `a.__eq__(b)` |
| `a <= b`    | `a.__le__(b)` |
| `a < b`     | `a.__lt__(b)` |
| `a >= b`    | `a.__ge__(b)` |
| `a > b`     | `a.__gt__(b)` |
| `repr(a)`   | `a.__repr__()` |
| `hash(a)`   | `a.__hash__()` |


> 关于`__radd__`与`__rsub__`

```python
>>> 2 + 0.2
2.2
>>> 0.2 + 2
2.2
>>> (2).__add__(0.2)
NotImplemented
>>> (2).__radd__(0.2)
NotImplemented
>>> (0.2).__add__(2)
2.2
>>> (0.2).__radd__(2)
2.2
>>> 2 - 0.2
1.8
>>> (2).__sub__(0.2)
NotImplemented
>>> (0.2).__rsub__(2)
1.8
```


```python
def numerator(f):
    return f.numerator

def denominator(f):
    return f.denominator
```

Design discussion. This is the old interface of the fraction operations.
Should the new interface be implemented using these functions? For example:

```python
class Fraction:
    ...
    def __add__(self, other):
        return add_frac(self, other)
```

Or should the old interface be supported in terms of the new interface?
For example:

```python
def add_frac(a, b):
    return a + b

def add_frac(a, b):
    ...
def sub_frac(a, b):
    ...
def mul_frac(a, b):
    ...
def div_frac(a, b):
    ...
```


The old unit tests must still pass (legacy code)

```python
def test_frac():
    a = make_frac(4, 6)
    assert (numerator(a), denominator(a)) == (2, 3)

    b = make_frac(-3, -4)
    assert (numerator(b), denominator(b)) == (3, 4)

    c = make_frac(3, -4)
    assert (numerator(c), denominator(c)) == (-3, 4)

    d = add_frac(a, b)
    assert (numerator(d), denominator(d)) == (17, 12)

    e = sub_frac(a, b)
    assert (numerator(e), denominator(e)) == (-1, 12)

    f = mul_frac(a, b)
    assert (numerator(f), denominator(f)) == (1, 2)

    g = div_frac(a, b)
    assert (numerator(g), denominator(g)) == (8, 9)

    print("Good fractions")

test_frac()
```

New unit tests. These manipulate fractions as proper Python numbers.

```python
def test_math():
    a = Fraction(4, 6)
    assert (a.numerator, a.denominator) == (2, 3)

    b = Fraction(-3, -4)
    assert (b.numerator, b.denominator) == (3, 4)

    # Requires the __add__() method
    c = a + b
    assert (c.numerator, c.denominator) == (17, 12)

    # Requires the __sub__() method
    d = a - b
    assert (d.numerator, d.denominator) == (-1, 12)

    # Requires the __mul__() method
    e = a * b
    assert (e.numerator, e.denominator) == (1, 2)

    # Requires the __truediv__() method
    f = a / b
    assert (f.numerator, f.denominator) == (8, 9)

    # Mixed type operations. Note: Python integers
    g = a + 1
    assert (g.numerator, g.denominator) == (5, 3)

    # Requires the __radd__() method
    g = 1 + a
    assert (g.numerator, g.denominator) == (5, 3)

    # Requires the __rsub__() method
    g = 1 - a
    assert (g.numerator, g.denominator) == (1, 3)

    h = a * 10
    assert (h.numerator, h.denominator) == (20, 3)

    # Requires the __rmul__() method
    h = 10 * a
    assert (h.numerator, h.denominator) == (20, 3)

    # Comparisons. For these, you'll need to implement
    # methods such as __eq__(), __ne__(), __lt__(), __le__(),
    # __gt__(), and __ge__().

    # To compare fractions you can perform comparisons like this:

    a = Fraction(2, 3)
    b = Fraction(4, 5)

    # a < b ==> a.numerator * b.denominator < a.denominator * b.numerator

    assert a != b  # 只实现了__eq__ 就支持 == 和 !=
    assert a == Fraction(2, 3)
    assert a < b  # 只实现了__lt__和__le__ 支持 <,<=,>,>=
    assert a <= b
    assert b > a
    assert b >= a
    print("Good fractions In new version")
```

## Niceties

There are certain things you can do to make your objects play nicer
with the rest of Python. These include nice printing, debugging,
and numeric conversions.

Modify your Fraction class so that it additionally passes the following tests

```python
def test_nice():
    a = Fraction(3, 2)

    assert str(a) == '3/2'    # Requires the __str__() method
    assert repr(a) == 'Fraction(3, 2)'    # Requires the __repr__() method
    assert float(a) == 1.5    # Requires the __float__() method
    assert int(a) == 1    # Requires the __int__() method

    # Special cases of nice output
    b = Fraction(2, 1)
    assert str(b) == '2'

    c = Fraction(0, 2)
    assert str(c) == '0'

    print('Nice fractions')
```

## 实现

[exercise_06.py](./code/fractions/exercise_06.py)

```python
from typing import Union, overload

# We will define a proper class
class Fraction:
    def __init__(self, numerator, denominator):
        d = gcd(numerator, denominator)
        self.numerator = numerator // d
        self.denominator = denominator // d

    # Define various magic methods for Python operators

    @overload
    def __add__(self, other: Fraction) -> Fraction: ...
    @overload
    def __add__(self, other: int) -> Fraction: ...
    def __add__(self, other):
        return Fraction(
            self.numerator * other.denominator + self.denominator * other.numerator,
            self.denominator * other.denominator,
        )

    __radd__ = __add__  # a + b = b + a

    # 相比使用overload使用Union更加方便
    def __sub__(self, other: Union[Fraction, int]) -> Fraction:
        return Fraction(
            self.numerator * other.denominator - self.denominator * other.numerator,
            self.denominator * other.denominator,
        )

    def __rsub__(self, other: Union[Fraction, int]):
        return Fraction(
            other.numerator * self.denominator - other.denominator * self.numerator,
            other.denominator * self.denominator,
        )

    def __mul__(self, other: Union[Fraction, int]) -> Fraction:
        return Fraction(
            self.numerator * other.numerator,
            self.denominator * other.denominator,
        )

    __rmul__ = __mul__  # a * b = b * a

    def __truediv__(self, other: Union[Fraction, int]) -> Fraction:
        return Fraction(
            self.numerator * other.denominator, self.denominator * other.numerator
        )

    def __rtruediv__(self, other):
        return Fraction(
            self.denominator * other.numerator, self.numerator * other.denominator
        )

    def __str__(self) -> str:
        if not self.numerator:
            return "0"
        return (
            f"{self.numerator}/{self.denominator}"
            if self.denominator != 1
            else f"{self.numerator}"
        )

    def __float__(self):
        return self.numerator / self.denominator

    def __int__(self):
        return self.numerator // self.denominator

    def __repr__(self) -> str:
        return f"Fraction({self.numerator}, {self.denominator})"

    def __eq__(self, other: Fraction | int) -> bool:
        return self.numerator * other.denominator == self.denominator * other.numerator

    def __lt__(self, other: Fraction | int) -> bool:
        return self.numerator * other.denominator < self.denominator * other.numerator

    def __le__(self, other: Fraction | int) -> bool:
        return self.numerator * other.denominator <= self.denominator * other.numerator


def add_frac(a, b):
    return a + b


def sub_frac(a, b):
    return a - b


def mul_frac(a, b):
    return a * b


def div_frac(a, b):
    return a / b
```

测试

```python
>>> a = Fraction(4,6)
>>> a
Fraction(numerator=2,denominator=3)
>>> 1 + a
Fraction(numerator=5,denominator=3)
>>> a + 1
Fraction(numerator=5,denominator=3)
>>> 1 - a
Fraction(numerator=1,denominator=3)
>>> a - 1
Fraction(numerator=-1,denominator=3)
>>> a * 3
Fraction(numerator=2,denominator=1)
>>> 3 * a
Fraction(numerator=2,denominator=1)
>>> a / 2
Fraction(numerator=1,denominator=3)
>>> 2 / a
Fraction(numerator=3,denominator=1)
>>> a / a
Fraction(numerator=1,denominator=1)
```