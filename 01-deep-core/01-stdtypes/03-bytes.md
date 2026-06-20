
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