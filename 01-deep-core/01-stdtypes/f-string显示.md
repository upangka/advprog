
| 符号 | 含义 |
|------|------|
| `:` | 格式说明符的开始 |
| `>` | 右对齐（right-aligned） |
| `<` | 左对齐（left-aligned） |
| `^` | 居中对齐（center-aligned） |
| `10` | 最小宽度为 10 个字符 |

```python
>>> # 右对齐
>>> l = "|{:>10}|{:>10}|{:>10}|".format('pkmer','Python','666')
>>> print(l)
|     pkmer|    Python|       666|
>>> # 居中对齐
>>> l = "|{:^10}|{:^10}|{:^10}|".format('pkmer','Python','666')
>>> print(l)
|  pkmer   |  Python  |   666    |
>>> # 左对齐
>>> l = "|{:<10}|{:<10}|{:<10}|".format('pkmer','Python','666')
>>> print(l)
|pkmer     |Python    |666       |
>>> # f-string
>>> name, language, num = ('pkmer','Python','666')
>>> print(f"|{name:^10}|{language:^10}|{num:^10}|")
|  pkmer   |  Python  |   666    |
```

```sh
|      pkmer|   ← 10 个字符宽
  ↑↑↑↑↑     ← 5 个空格
  pkmer     ← 5 个字符
```