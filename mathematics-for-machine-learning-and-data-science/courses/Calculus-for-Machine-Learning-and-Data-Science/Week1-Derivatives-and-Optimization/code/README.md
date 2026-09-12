# Lab1 符号微分、数值微分、自动微分

Python中求导数三种方法：符号微分、数值微分、自动微分 [Lab1_differentiation_in_Python_Symbolic_Numerical_and_Automatic.ipynb](./Lab1_differentiation_in_Python_Symbolic_Numerical_and_Automatic.ipynb)

| 方法     | 工具  | 原理               | 精度 | 性能                   |
| -------- | ----- | ------------------ | ---- | ---------------------- |
| 符号微分 | SymPy | 像人一样按规则推导 | 精确 | 慢（表达式膨胀）       |
| 数值微分 | NumPy | 用差分近似导数     | 近似 | 慢（每次都要算函数值） |
| 自动微分 | JAX   | 计算图 + 链式法则  | 精确 | 快（工业级）           |
