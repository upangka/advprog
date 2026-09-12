# Lab1 符号微分、数值微分、自动微分

Python中求导数三种方法：符号微分、数值微分、自动微分 [Lab1_differentiation_in_Python_Symbolic_Numerical_and_Automatic.ipynb](./Lab1_differentiation_in_Python_Symbolic_Numerical_and_Automatic.ipynb)

| 方法         | 工具    | 原理                  | 精度     | 速度               | 适合场景         |
| ------------ | ------- | --------------------- | -------- | ------------------ | ---------------- |
| 符号微分     | SymPy   | 按数学规则推导        | 精确     | 慢（表达式膨胀）   | 简单函数、教学   |
| 数值微分     | NumPy   | 差分近似              | 近似     | 慢（每次重新求值） | 快速估算         |
| **自动微分** | **JAX** | **计算图 + 链式法则** | **精确** | **快**             | **神经网络训练** |

# Lab2 优化单变量函数：损失最小化

用「供应商选择」这个真实问题，演示单变量优化的完整流程。

业务背景：公司要从供应商 A 和 B 采购产品 P，每月采购量固定。需要决定「从 A 采购的比例 ω」，让 12 个月的总成本波动最小。

[Lab2_Optimizing_Functions_of_One_variables_cost_minimization.ipynb](./Lab2_Optimizing_Functions_of_One_variables_cost_minimization.ipynb)

**1. 单月成本**

$$
f^i(\omega) = p_A^i \cdot \omega + p_B^i \cdot (1 - \omega)
$$

**2. 损失函数（方差）**

$$
\mathcal{L}(\omega) = \frac{1}{k} \sum_{i=1}^{k} \left( f^i(\omega) - \overline{f(\omega)} \right)^2
$$

**3. 优化目标**

$$
\min_{\omega \in [0,1]} \mathcal{L}(\omega)
$$
