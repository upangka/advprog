

```python
>>> b"" == bytes()
True
>>> len(b"")
0
>>> len(bytes())
0
```


```python
>>> "hi".encode("utf-8")
b'hi'
>>> b'hi'.decode('utf-8')
'hi'
```