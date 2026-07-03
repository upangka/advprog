# 重新抛出异常

当except捕获异常后，可以有多种方式重新抛出异常

1. 原原本本的重新抛出异常

```python
try:
  file = open('foo.txt','rt')
except FileNotFoundError:
  print("Well, that didn't work")
  raise # 重新抛出当前异常
```

# 异常层次

常见的异常根类与基础类

| 异常类型 | 描述 |
|---------|------|
| BaseException | 所有异常的根类 |
| Exception | 所有与程序相关的错误的基础类型 |
| LookupError | 所有与容器查找相关的错误的基础类型 |


# 自定义新的异常
