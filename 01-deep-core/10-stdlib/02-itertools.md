
# product

product 返回多个列表/元组/范围的全组合（即笛卡尔积）

[product_ex.py](./code/itertoolslib/product_ex.py)
```python
import itertools

# 三个列表的全组合
colors = ["red", "blue"]
sizes = ["S", "M"]
styles = ["modern", "classic"]

count = 0
# 输出 2 × 2 × 2 = 8 种组合
for color, size, style in itertools.product(colors, sizes, styles):
    print(f"({color=}, {size=}, {style=})")
    count += 1
print(f"一共{count}组合")
```

```sh
(color='red', size='S', style='modern')
(color='red', size='S', style='classic')
(color='red', size='M', style='modern')
(color='red', size='M', style='classic')
(color='blue', size='S', style='modern')
(color='blue', size='S', style='classic')
(color='blue', size='M', style='modern')
(color='blue', size='M', style='classic')
一共8组合
```

| 函数 | 数学描述 | 核心区别 |
|------|----------|----------|
| `product` | 笛卡尔积 | 每个位置独立选（各个变量之间互不影响），允许重复 |
| `permutations` | 排列 | 从集合里挑几个，顺序敏感，不允许重复 |
| `combinations` | 组合 | 从集合里挑几个，顺序不敏感，不允许重复 |