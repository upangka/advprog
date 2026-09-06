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

# np.linalg求解线性方程组3x3

[Lab_1_solving_linear_systems_3_variables.ipynb](./Lab_1_solving_linear_systems_3_variables.ipynb)

## 🎯 这个实验的核心目标

| 目标                                | 说明                                                    |
| :---------------------------------- | :------------------------------------------------------ |
| **用矩阵表示 3x3 线性方程组**       | 将系数矩阵 \(A\) 和常数向量 \(b\) 分离                  |
| **用 `np.linalg.solve()` 求解**     | 直接解出 \(x_1, x_2, x_3\)                              |
| **用 `np.linalg.det()` 验证奇异性** | 行列式非零 → 唯一解；行列式为零 → 奇异 → `solve()` 报错 |
| **理解异常处理**                    | 奇异矩阵会导致soive报错 `LinAlgError`                   |

---

## 📝 本实验用到的 NumPy API

| 名称                    | 说明                                      |
| :---------------------- | :---------------------------------------- |
| `np.linalg.solve(A, b)` | 解线性方程组 $Ax = b$，返回解向量 $x$     |
| `np.linalg.det(A)`      | 计算方阵 $A$ 的行列式                     |
| `np.dtype(float)`       | 指定数组元素类型为浮点数                  |
| `np.shape(A)`           | 返回数组的形状，如 `(3, 3)` 或 `(3,)`     |
| `LinAlgError`           | 奇异矩阵时 `np.linalg.solve()` 抛出的异常 |

---

## 📌 实验最后的提示（非常重要）

> _"This is why the next assignment will be in Gaussian Elimination, a method to solve linear systems. Remember that np.linalg.solve gives an error if there are no or infinitely many solutions. When using it, you will need to check for that case to prevent your program from crashing."_

翻译：

> _"这就是为什么下一个实验将围绕**高斯消元法**展开。请记住，`np.linalg.solve` 在系统无解或无穷多解时会报错。使用它时，你需要检查这种情况，以防止程序崩溃。"_

**潜台词**：

- `np.linalg.solve()` 是“黑盒”工具，方便但隐藏了细节
- 下一个实验（Graded Lab）会让你**手写高斯消元法**，深入理解行化简的过程
- 你需要学会**如何判断奇异矩阵**，并用 `try-except` 优雅地处理异常
