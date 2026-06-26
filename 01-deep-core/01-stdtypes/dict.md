# Merging Dicts with symbol `|`

```python
>>> d1 = {'a':1,'b':2}
>>> d2 = {'b':3,'c':5}
>>> d1 | d2
{'a': 1, 'b': 3, 'c': 5}
>>> d1
{'a': 1, 'b': 2}
>>> d1 |= d2
>>> d1
{'a': 1, 'b': 3, 'c': 5}
```

# Unpacking Dict

```python
>>> def dump(**kwargs):
...     print(f"{type(kwargs)}, {kwargs=}")
>>> dump(**{'x': 1},y=2,**{'z':3})   #pass dict also need unpacking
<class 'dict'>, kwargs={'x': 1, 'y': 2, 'z': 3}
```

# Pattern Matching

[exercise_01.py](./code/dict/exercise_01.py)

> List Matching

```sh
>>> match exp:
...     case ['lambda',[*params],*body]:
...         print(f"{params=} \n{body=}")
...     case _:
...         raise ValueError()
...
params=['a', 'b']
body=[['*', ['a', 'b'], 100]]

# Note:diff between [*body] with *body

>>> match exp:
...     case ['lambda',[*params],[*body]]:
...         print(f"{params=} \n{body=}")
...     case _:
...         raise ValueError()
...
params=['a', 'b']
body=['*', ['a', 'b'], 100]
```

# Equals

```python
# True（嵌套字典也递归比较内容）
>>> d1 = {"a": [1, 2], "b": {"x": 10}}
>>> d2 = {"b": {"x": 10}, "a": [1, 2]}
>>> d1 == d2
True
# False（列表顺序不同，所以不等）
>>> d1 = {"b": {"x": 10}, "a": [2, 1]}
>>> d2 = {"b": {"x": 10}, "a": [1, 2]}
>>> d1 == d2
False
```

只比较内容

```python
>>> class A:
...     pass
...
>>> a = A()
>>> a.a=2
>>> a.__dict__
{'a': 2}
>>> vars(a)
{'a': 2}
>>> all([dict(a=2) == vars(a),dict(a=2) == a.__dict__])
True
```

# dict(dict)

```python
>>> a = dict(author='dabeaz',book='Python精粹')
>>> b = dict(a)
>>> a == b
True
>>> a is b
False
>>> a
{'author': 'dabeaz', 'book': 'Python精粹'}
>>> b
{'author': 'dabeaz', 'book': 'Python精粹'}
```

# update

```sh
>>> # update 方法
>>> a.update(oop='Greate OOP')
>>> a
{'author': 'dabeaz', 'book': 'Python精粹', 'oop': 'Greate OOP'}
>>> # 直接定义新的key和value
>>> b['func']="High order function"
>>> b
{'author': 'dabeaz', 'book': 'Python精粹', 'func': 'High order function'}
```