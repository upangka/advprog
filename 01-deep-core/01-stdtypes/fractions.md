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