使用uv搭建实验环境搭建与安装相关package

```sh
pkmer@DESKTOP-2368UCO:code
$ uv init --no-workspace
Initialized project `code`

pkmer@DESKTOP-2368UCO:code
$ uv add numpy jupyter matplotlib
```

启动

```sh
uv run jupyter lab
# 后台启动
uv run jupyter lab &
```

# Introduction to numpy arrays

[Lab_1_introduction_to_numpy_arrays.ipynb](./Lab_1_introduction_to_numpy_arrays.ipynb)

| 名称               | 说明                                                  |
| :----------------- | :---------------------------------------------------- |
| `np.array()`       | 创建一个 NumPy 数组                                   |
| `np.arange()`      | 在给定区间内以固定步长生成等间距值（不包含结束值）    |
| `np.linspace()`    | 在给定区间内生成固定数量的等间距值（包含结束值）      |
| `np.ones()`        | 创建指定形状的全 1 数组                               |
| `np.zeros()`       | 创建指定形状的全 0 数组（已初始化）                   |
| `np.empty()`       | 创建指定形状的未初始化数组（值随机，速度更快）        |
| `np.random.rand()` | 创建指定形状的 [0, 1) 随机数数组                      |
| `np.reshape()`     | 改变数组形状                                          |
| `np.vstack()`      | 垂直堆叠数组                                          |
| `np.hstack()`      | 水平堆叠数组                                          |
| `ndarray.ndim`     | 数组的维度数                                          |
| `ndarray.shape`    | 数组的形状，返回元组如 `(2, 3)`                       |
| `ndarray.size`     | 数组的元素总数                                        |
| `ndarray.dtype`    | 数组元素的数据类型（如 `int`, `float`, `<U23`）       |
| `numpy.str_`       | NumPy 中的 Unicode 字符串类型，是 Python `str` 的子类 |
| `<U23`             | 表示最大长度为 23 的 Unicode 字符串，小端存储         |
