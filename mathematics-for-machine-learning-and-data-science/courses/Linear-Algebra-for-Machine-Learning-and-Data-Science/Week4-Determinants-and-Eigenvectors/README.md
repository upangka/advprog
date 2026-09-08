# Determinants In-depth(深入理解行列式)

行列式不是"一堆数字乘来乘去"——它是线性变换对空间的"缩放指示器"。绝对值告诉你放大多少倍，符号告诉你有没有翻面，0 告诉你空间被压扁了。

## Singularity and rank of linear transformations

> 线性变换的奇异性与秩

一个线性变换(矩阵)是奇异还是非奇异，取决于它把整个平面映射到了多大的空间——是完整的平面，还是一条线，还是一个点。而这个空间的维度，就是矩阵的秩。

1. 非奇异变换 = 平面 → 平面（满秩）
2. 奇异变换 = 平面 → 直线或点（秩亏）
3. 秩 = 变换后空间的维度。

![alt text](./images/singulartity-of-linear-transformations.png)
