import matplotlib.pyplot as plt
import numpy as np

def plot_lines(M):
    """
    绘制 2x2 线性方程组对应的两条直线，并可视化其解的情况。
    
    参数:
        M: 增广矩阵，形状为 (2, 3)，形式为 [[a, b, c], [d, e, f]]
           对应方程组:
               a*x1 + b*x2 = c
               d*x1 + e*x2 = f
    """
    
    # 生成 x1 轴上的 100 个等间距点，范围从 -10 到 10
    # 这些点将用于绘制两条直线
    x_1 = np.linspace(-10, 10, 100)
    
    # 根据第一行方程 a*x1 + b*x2 = c，解出 x2 = (c - a*x1) / b
    # M[0,0] = a, M[0,1] = b, M[0,2] = c
    x_2_line_1 = (M[0, 2] - M[0, 0] * x_1) / M[0, 1]
    
    # 根据第二行方程 d*x1 + e*x2 = f，解出 x2 = (f - d*x1) / e
    # M[1,0] = d, M[1,1] = e, M[1,2] = f
    x_2_line_2 = (M[1, 2] - M[1, 0] * x_1) / M[1, 1]
    
    # 创建一个图形窗口，尺寸为 10x10 英寸
    # "_" 表示忽略返回的 figure 对象，只取 axes 对象
    _, ax = plt.subplots(figsize=(10, 10))
    
    # 绘制第一条直线
    # '-' 表示实线，linewidth=2 线宽为 2，color 为蓝色
    # label 显示直线的方程，自动计算斜率和截距并保留两位小数
    ax.plot(x_1, x_2_line_1, '-', linewidth=2, color='#0075ff',
            label=f'$x_2={-M[0,0]/M[0,1]:.2f}x_1 + {M[0,2]/M[0,1]:.2f}$')
    
    # 绘制第二条直线
    # '-' 表示实线，linewidth=2 线宽为 2，color 为橙色
    ax.plot(x_1, x_2_line_2, '-', linewidth=2, color='#ff7300',
            label=f'$x_2={-M[1,0]/M[1,1]:.2f}x_1 + {M[1,2]/M[1,1]:.2f}$')
    
    # 从增广矩阵 M 中提取系数矩阵 A（去掉最后一列）
    # M[:, 0:-1] 表示取所有行，从第 0 列到倒数第 2 列
    A = M[:, 0:-1]
    
    # 从增广矩阵 M 中提取常数向量 b（只取最后一列）
    # M[:, -1::] 表示取所有行，只取最后一列
    # .flatten() 将列向量展平成一维数组，从 (2,1) 变成 (2,)
    b = M[:, -1::].flatten()
    
    # 计算系数矩阵 A 的行列式
    d = np.linalg.det(A)
    
    # 如果行列式不为 0，说明矩阵非奇异，系统有唯一解
    if d != 0:
        # 使用 NumPy 的线性代数求解器解方程组 Ax = b
        solution = np.linalg.solve(A, b)
        
        # 在图上用红色空心圆标记解的位置
        # '-o' 表示同时画线和点，mfc='none' 表示圆内部空心
        # markersize=10 标记大小，markeredgecolor 红色边框
        # markeredgewidth=2 边框宽度为 2
        ax.plot(solution[0], solution[1], '-o', mfc='none',
                markersize=10, markeredgecolor='#ff0000', markeredgewidth=2)
        
        # 在解的位置旁边添加文本标签，显示 (x1, x2) 的数值
        # solution[0]-0.25, solution[1]+0.75 表示文本偏移量，避免覆盖点
        ax.text(solution[0] - 0.25, solution[1] + 0.75,
                f'$(${solution[0]:.0f}$,{solution[1]:.0f})$', fontsize=14)
    
    # 设置 x 轴和 y 轴刻度标签的字体大小
    ax.tick_params(axis='x', labelsize=14)
    ax.tick_params(axis='y', labelsize=14)
    
    # 设置 x 轴和 y 轴的刻度为 -10 到 10 之间的整数
    ax.set_xticks(np.arange(-10, 10))
    ax.set_yticks(np.arange(-10, 10))
    
    # 设置 x 轴和 y 轴的标签
    plt.xlabel('$x_1$', size=14)
    plt.ylabel('$x_2$', size=14)
    
    # 显示图例，位置在右上角，字体大小为 14
    plt.legend(loc='upper right', fontsize=14)
    
    # 设置 x 轴和 y 轴的显示范围均为 -10 到 10
    plt.axis([-10, 10, -10, 10])
    
    # 显示网格线
    plt.grid()
    
    # 设置坐标轴等比例缩放，保证圆的形状不被拉伸
    # 这样两条直线垂直时看起来才是真正的垂直
    plt.gca().set_aspect("equal")
    
    # 在屏幕上显示图形
    plt.show()