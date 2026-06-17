

[after.py](./code/interfacee/after.py)

Mary has been pondering the mysteries of the universe, time, and
function evaluation. In this project, we're going to sneak in and
join her. Let's peek inside her mind...

- pondering /ˈpɑːn.dər.ɪŋ/ v. 沉思；深思；仔细思考（指对某个问题或主题进行认真、深入的思考，通常带有哲学性或探索性，不是简单的随便想想。
- mysteries /ˈmɪs.tər.iz/ n. 奥秘；神秘事物（指那些难以理解、尚未被完全揭示或充满未知的事物，常带有哲学或宗教色彩。
- evaluation /ɪˌvæl.juˈeɪ.ʃən/ n. 求值；评估；计算（指对某个表达式、函数或代码进行计算并得出结果的过程。在编程语境中，特指解释器或编译器执行代码并返回一个值的过程。在上下文中，"function evaluation" 指的是"函数求值"——即调用一个函数并执行其内部代码以获得返回值的过程。
- sneak /sniːk/ v. 偷偷进入；潜入（指悄悄地、不被注意地进入某个地方或了解某件事情，带有一种鬼鬼祟祟或调皮意味。在上下文中，"we're going to sneak in and join her" 的意思是"我们要偷偷溜进去加入她"，这是一种拟人化、俏皮的写法，让读者感觉像是悄悄潜入 Mary 的思想世界，窥探她对编程问题的思考过程，增加了课程的趣味性和故事感）
- peek inside /piːk ɪnˈsaɪd/ v. 偷看内部；窥视内部（指悄悄地、好奇地看向某个事物的内部，了解其内容或运作方式。在上下文中，"Let's peek inside her mind..." 的意思是"让我们窥视一下她的内心……"，这是一种文学性表达，指了解 Mary 大脑中的想法和思考内容。与 sneak 呼应，延续了"偷偷潜入"的俏皮叙事风格，让读者感觉自己正在悄悄地观察 Mary 对函数求值问题的思考过程）


... ah, we see that Mary is currently pondering the problem of
submitting work to cloud services. The focus of her thinking is not
so much on the low-level mechanics, but on the top-level programming
interface for it. To explore this, she has written the following
small function that accepts a time delay and a function callback.
The evaluation of the supplied function is delayed and its final
returned. Very exciting! Although it's not quite the same as
actually executing a function in the cloud, it at least mimics the
performance of doing so.

- mimics /ˈmɪm.ɪks/ v. 模仿；模拟；仿效（指通过某种方式复制或再现另一个事物的行为、外观或效果。在上下文中，"it at least mimics the performance of doing so" 的意思是"它至少模拟了这样做的性能表现"，指的是 after() 函数通过 time.sleep() 来模拟云端服务调用的延迟效果，虽然不是真正的云端执行，但至少从"耗时"的角度复现了云端调用的体验。这个词强调的是一种表面上的相似性，用于替代或仿效真实场景的行为）

```python
import time

def after(seconds, func):
    time.sleep(seconds)  # Emulate the "cloud"
    return func()
```

Proceed to exercise 1 in ex1.py


## Exercise 1

[exercise_01.py](./code/interfacee/exercise_01.py)

Your first exercise is to make sure you know how to use the function with
a simple "Hello World" example.

```python
def greeting():
    print("Hello World, From ShenZhen, China :)".center(60, "."))

after(3, greeting)
```

How do you use the `greeting()` function with the `after()` function?
That is, have the after() function call greeting() after 10 seconds.


## Exercise 2

[exercise_02.py](./code/interfacee/exercise_02.py)

It seems that the `after()` function only works if you give it a
function taking no arguments. Is there any way to make it work with
a function that takes any set of arguments? Can you do this without
making any code changes to the `after()` function or the function
that's be supplied as input?

You are NOT allowed to change any part of the `after()` function
from after import after

You are NOT allowed to change any part of this function

```python
def add(x, y):
    print(f'Adding {x} + {y} -> {x + y}')
    return x + y
```

This doesn't work. Why? Can you modify it to make it work?

```python
result = after(10, add(2, 3))
```

需要做一些适配Adapter工作

> Approach 1: A helper function

```python
def help():
    return add(2, 3)

after(3, help)
```

> Approach 2: `Lambda`. (Same idea)

```python
after(3, lambda: add(2, 3))
```

> Aprroach 3: Freezing Arguments with `functools.parital` 

```python
import functools

p = functools.partial(add, 2, 3)
print(f"查看已经绑定的参数: f={p.func} args={p.args} kwargs={p.keywords}")

"""
属性	        类型	    内容
p.func	        function   被包装的原始函数
p.args	        tuple	   已绑定的位置参数（按顺序）
p.keywords	    dict	   已绑定的关键字参数
"""

after(3, p)
```


**Thought Experiment**:

How would you use the after() function to carry out the following

operation after 5 seconds?

```python
   add(add(1,2), add(3,4))
```

Before you begin, what is this operation even doing? What
behavior do you expect to see?

**注意**: `5s`之后统一执行，不会提前计算`add(1,2)`等参数，换另外一个角度，由于Mary是要上传到云端执行的函数，所有的运算都将在云端进行执行。
```python
>>> after(5, lambda: add(add(1,2),add(3,5)))
Adding 1 + 2 -> 3   # 等待5s后才输出
Adding 3 + 5 -> 8
Adding 3 + 8 -> 11
11
```

---

**注意**: 两个参数会先立即执行， `5s`之后输出最后的结果, 换另外一个角度，由于Mary是上传到云端执行的，那么现在这个函数，参数的计算会在客户端进行完成，之后最终的结果是在云端进行完成

```python
>>> after(5,functools.partial(add,add(1,2),add(3,5)))
Adding 1 + 2 -> 3  # 立即输出
Adding 3 + 5 -> 8
Adding 3 + 8 -> 11  # 等待5s之后输出
11
```

## Exercise 3

[exercise_03.py](./code/interfacee/exercise_03.py)

Mary has been further pondering the usage of the `after()` function. Should she make it easier for users to supply arguments to the provided function? For example, to simplify the problem addressed in Exercise 2.

This is a surprisingly nuanced problem because Python functions can be called in many different ways. For example:

```python
def func(x, y, z):
    ...
    func(1, 2, 3)    # Positional arguments
    func(x=1, y=2, z=3)  # Keyword arguments
    func(1, z=3, y=2)  # Position/keyword argument mix

    args = (1, 2, 3)
    func(*args)    # Passing a tuple as positional arguments

    kwargs = {'x': 1, 'y': 2, 'z': 3}
    func(**kwargs)  # Passing a dict as keyword arguments
```

To make matters even more complicated, a function can force the
use of **keyword arguments**:

```python
def func(x, *, y):
    ...
func(1, 2)    # Error. y not supplied by keyword
func(1, y=2)    # Ok!
```

Plus, there are functions that accept any number of **positional
or keyword arguments**:

```python
def func(*args, **kwargs):
    ...
```

And in more recent versions of Python, **positional-only functions**:

```python
def func(x, y, /, z):
    ...
func(1, 2, 3)    # OK
func(1, 2, z=3)  # OK
func(1, y=2, z=3) # ERROR.
```


To explore all of the above options, Mary has written 3 variants of
the `after()` function.

**Option 1**: Original implementation. No arguments.

```python
def after_1(seconds, func):
    time.sleep(seconds)
    return func()
```

**Option 2**: Extra arguments are passed as explicit tuple/dict

```python
def after_2(seconds, func, args=(), kwargs={}):
    time.sleep(seconds)
    return func(*args, **kwargs)
```


**Option 3**: Extra arguments provided via *args and **kwargs

```python
def after_3(seconds, func, *args, **kwargs):
    time.sleep(seconds)
    return func(*args, **kwargs)
```

---

> **Part 1**:

You first task is to show how you would go about using the above
functions with the `add()` function from before--using both positional
and keyword arguments.

```python
def add(x, y):    # You are NOT allowed to change this function
    print(f'Adding {x} + {y} -> {x + y}')
    return x + y
```

You must fix each of these to work correctly. Uncomment each line.

```python
after_1(1, lambda: add(2, 3))
after_1(1, lambda: add(x=2, y=3))

after_2(1, add, (2, 3))
after_2(1, add, kwargs={'x':2, 'y':3})
# Commentary: I might be inclined to write it like this so I could
# keep the syntax of kwargs.
after_2(1, add, kwargs=dict(x=2, y=3))

after_3(1, add, 2, 3)
after_3(1, add, x=2, y=3)
```

- Commentary /ˈkɑːmənteri/ n. 评论；注解；解说（指对某个主题、事件或文本的评述和解释。）

--- 

> **Part 2**:

Ben looks at the code and perversely asks what happens if you try to
use `after()` to call itself?

"What kind of question is that?!?", exclaims Mary.

"Well, if your goal is to make the function general purpose, then
surely it should be capable of calling itself", explained Ben.
"For example, something like this."

- perversely /pərˈvɜːrsli/ adv. 执拗地；反常地；故意作对地（指行为或想法与常理相悖，带有一种固执、爱钻牛角尖或故意唱反调的意味。

```python
after_1(1, lambda: after_1(1, lambda: add(2,3)))
after_1(1, lambda: after_1(seconds=1, func=lambda: add(2, 3)))
```

Make these work. Note: Our focus here is on the "after_" function,
not on the `add()` function.

```python
after_2(1, after_2, args=(1, add, (2, 3)))
# 直接用字段全部代替
after_2(1, after_2, kwargs=dict(seconds=1, func=add, args=(2, 3)))
# kwargs需要多处理一层
after_2(1, after_2, args=(1, add), kwargs={"kwargs": dict(x=2, y=3)})

after_3(1, after_3, 1, add, 2, 3)
after_3(1, after_3, 1, add, x=2, y=3)
```

---

> **Part 3**:

Your task is as follows. Decide which approach Mary should use going
forward and code it into a final after() function below. If you
think Mary should do something different than any of the proposed
solutions, code that instead. In all cases, be prepared to explain
your reasoning when you unleash this code on your coworkers...

```python
def after(seconds, func):
    # Final implementation. You decide what it is.
    ...
```