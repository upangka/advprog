# Lab1 符号微分、数值微分、自动微分

Python中求导数三种方法：符号微分、数值微分、自动微分 [Lab1_differentiation_in_Python_Symbolic_Numerical_and_Automatic.ipynb](./Lab1_differentiation_in_Python_Symbolic_Numerical_and_Automatic.ipynb)

| 方法         | 工具    | 原理                  | 精度     | 速度               | 适合场景         |
| ------------ | ------- | --------------------- | -------- | ------------------ | ---------------- |
| 符号微分     | SymPy   | 按数学规则推导        | 精确     | 慢（表达式膨胀）   | 简单函数、教学   |
| 数值微分     | NumPy   | 差分近似              | 近似     | 慢（每次重新求值） | 快速估算         |
| **自动微分** | **JAX** | **计算图 + 链式法则** | **精确** | **快**             | **神经网络训练** |
