
`locals`

```python
>>> code = """
... def f(msg):
...     print(f'{msg=}')"""
>>>
>>> locs = {}
>>> exec(code,locals=locs)
>>> locs
{'f': <function f at 0x7f0707a5b420>}
>>> locs['f']("Hi,World")
msg='Hi,World'
```

`globals`会携带内置的变量和函数

```python
>>> locs = {}
>>> # exec(code,locs)默认是 exec(code,globals=locs)
>>> # 得到全局的变量
>>> exec(code,locs)
>>> list(locs.keys())
['__builtins__', 'f']
```

