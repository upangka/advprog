 

 # Merging Dicts

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
