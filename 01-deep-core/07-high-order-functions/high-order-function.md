
1. Functions are data
2. Functions can create functions


# Functions are data

```python
>>> def f(x):
...     return x * 2
...
>>> items = [1,2,f]
>>> items
[1, 2, <function f at 0x7f18290777e0>]
>>> items[2](3)
6
```