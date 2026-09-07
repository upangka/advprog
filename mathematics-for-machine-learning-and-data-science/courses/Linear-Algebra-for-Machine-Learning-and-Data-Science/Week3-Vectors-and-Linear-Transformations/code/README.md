# Lab 1 vector operation

[Lab_1_vector_operations.ipynb](./Lab_1_vector_operations.ipynb)

| 目标                     | 说明                                                         |
| :----------------------- | :----------------------------------------------------------- |
| **理解向量的几何表示**   | 向量 = 有大小（magnitude）和方向（direction）的箭头          |
| **掌握向量运算**         | 标量乘法、向量加法、范数（长度）                             |
| **理解点积的两种定义**   | 代数定义（对应元素相乘再求和）和几何定义（与夹角余弦的关系） |
| **体验向量化计算的优势** | 对比循环版和向量化版的点积耗时，感受 **10-100 倍**的速度差异 |
| **理解向量相似度**       | 点积用于衡量词向量相似度，**归一化后就是余弦相似度**         |

# Lab 2 matrix multiplication

[Lab_2_matrix_multiplication](./Lab_2_matrix_multiplication.ipynb)

| 目标                         | 说明                                  |
| :--------------------------- | :------------------------------------ |
| **理解矩阵乘法的定义**       | 行 × 列 的点积，维度匹配规则          |
| **掌握矩阵乘法的实现**       | `np.matmul(A, B)` 和 `A @ B`          |
| **理解矩阵乘法的维度约束**   | A*A* 的列数必须等于 B*B* 的行数       |
| **了解 NumPy 的自动转置**    | 一维向量在 `np.matmul` 中会被自动处理 |
| **理解广播（Broadcasting）** | 标量运算自动扩展到整个矩阵            |

# Lab 3 linear transformations

[Lab_3_linear_transformations.ipynb](./Lab_3_linear_transformations.ipynb)

| 问题                           | 实验中的体现                                                     |
| ------------------------------ | ---------------------------------------------------------------- |
| **什么是变换？**               | 函数 $T : \mathbb{R}^2 \to \mathbb{R}^3$，把输入向量变成输出向量 |
| **什么是线性变换？**           | 满足 $T(kv) = kT(v)$ 和 $T(u + v) = T(u) + T(v)$                 |
| **矩阵和线性变换的关系？**     | 每个线性变换都对应一个矩阵，矩阵乘法 = 施加变换                  |
| **如何用矩阵表示变换？**       | 矩阵的列 = 标准基向量 $e_1, e_2$ 经过变换后的结果                |
| **常见几何变换的矩阵是什么？** | 水平缩放、垂直反射（本节课演示）                                 |
| **变换顺序为什么重要？**       | 矩阵乘法不交换，旋转+剪切 $\neq$ 剪切+旋转                       |

# Lab 4 Linear Transformations and Neural Network

[Lab_4_linear_transformations_and_neural_networks.ipynb](./Lab_4_linear_transformations_and_neural_networks.ipynb)

| 目标                   | 说明                                      |
| ---------------------- | ----------------------------------------- |
| 实现简单线性回归       | 用单感知机 + 1 个输入节点，拟合一条直线   |
| 理解神经网络的结构     | 输入层、权重、偏置、输出层、前向传播      |
| 用矩阵乘法实现前向传播 | $Z = WX + b$，一次性计算所有样本          |
| 理解训练过程           | 前向传播 → 计算代价 → 反向传播 → 更新参数 |
| 扩展到多元线性回归     | 用 2 个输入节点，预测房价                 |

```python
def nn_model(X, Y, num_iterations=10, print_cost=False):
   """
   神经网络模型
   """

    # 初始化参数W,b
    parameters = ...
    # Loop
    for i in range(0, num_iterations):

        // 向前传播预测
        Y_hat = forward_propagation(X, parameters)

        // 查看损失cost
        cost = compute_cost(Y_hat,Y)
        if print_cost:
            print ("Cost after iteration %i: %f" %(i, cost))

        // 反向传播 继续更新参数
        parameters = w3_tools.train_nn(parameters, Y_hat, X, Y)
    return parameters
```
