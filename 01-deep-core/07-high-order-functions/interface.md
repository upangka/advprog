

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

> Approach 1: A helper function

```python
def help():
    return add(2, 3)

after(3, help)
```

> Approach 2: Lambda. (Same idea)

```python
after(3, lambda: add(2, 3))
```

> Aprroach 3: functools.parital

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

```python
result = after(5, add(add(1,2), add(3,4)))    # Must modify!
```