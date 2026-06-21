
空字节

```python
>>> b"" == bytes()
True
>>> len(b"")
0
>>> len(bytes())
0
```

编码与解码

```python
>>> "hi".encode("utf-8")
b'hi'
>>> b'hi'.decode('utf-8')
'hi'
```

转化为整数

```python
>>> int(b'123')
123
>>> int(b'123    ')
123
>>> int(b'123\r\n')
123
>>> int(b'123\r')
123
>>> int(b'123\n')
123
```

计算

```python
>>> b'hi' + b' world'
b'hi world'
>>> b'.'.join([b'hi' for _ in range(6)])
b'hi.hi.hi.hi.hi.hi'
>>> b'hi~' * 6
b'hi~hi~hi~hi~hi~hi~'
```