import numpy as np
import matplotlib.pyplot as plt

def plot_transformation(T, v1, v2, vector_name='v'):
    """
    绘制线性变换 T 对两个基向量 v1, v2 的作用效果
    
    参数:
        T: 线性变换（矩阵或函数）
        v1: 第一个基向量（形状为 (2,) 的数组）
        v2: 第二个基向量（形状为 (2,) 的数组）
        vector_name: 向量名称（用于图例标签，默认为 'v'）
    
    返回:
        fig: matplotlib 图形对象
    """
    
    # 定义颜色
    color_original = "#129cab"      # 原始向量颜色（蓝绿色）
    color_transformed = "#cc8933"   # 变换后向量颜色（橙色）
    
    # 计算变换后的两个基向量
    v1_transformed = T @ v1
    v2_transformed = T @ v2
    
    # 创建图形和坐标轴，尺寸为 7x7 英寸
    fig, ax = plt.subplots(figsize=(7, 7))
    
    # 设置坐标轴刻度标签的字体大小
    ax.tick_params(axis='x', labelsize=14)
    ax.tick_params(axis='y', labelsize=14)
    
    # 计算所有点的最小值和最大值，用于确定坐标轴范围
    # 包括：原始向量、变换后的向量、变换后向量的和（平行四边形对角线）
    vmin = np.floor(np.min([v1, v2, v1_transformed, v2_transformed, 
                             (v1_transformed + v2_transformed)]) - 0.5)
    vmax = np.ceil(np.max([v1, v2, v1_transformed, v2_transformed, 
                           (v1_transformed + v2_transformed)]) + 0.5)
    
    # 设置坐标轴刻度和显示范围
    ax.set_xticks(np.arange(vmin, vmax))
    ax.set_yticks(np.arange(vmin, vmax))
    plt.axis([vmin, vmax, vmin, vmax])
    
    # === 绘制原始向量（蓝色） ===
    
    # 用箭头绘制 v1 和 v2，起点在原点 (0,0)
    plt.quiver([0, 0], [0, 0], 
               [v1[0], v2[0]], [v1[1], v2[1]], 
               color=color_original, angles='xy', scale_units='xy', scale=1)
    
    # 绘制原始向量张成的平行四边形
    # 顺序: 原点 → v2 → v1+v2 → v1 → 原点
    plt.plot([0, v2[0], v1[0] + v2[0], v1[0]], 
             [0, v2[1], v1[1] + v2[1], v1[1]], 
             color=color_original)
    
    # 为 v1 添加标签，计算偏移量避免与箭头重叠
    v1_sgn = 0.02 * (vmax - vmin) * np.array([[1] if i == 0 else [i] for i in np.sign(v1)])
    ax.text(v1[0] + v1_sgn[0], v1[1], f'${vector_name}_1$', 
            fontsize=14, color=color_original)
    
    # 为 v2 添加标签
    v2_sgn = 0.02 * (vmax - vmin) * np.array([[1] if i == 0 else [i] for i in np.sign(v2)])
    ax.text(v2[0], v2[1] + v2_sgn[1], f'${vector_name}_2$', 
            fontsize=14, color=color_original)
    
    # === 绘制变换后的向量（橙色） ===
    
    # 用箭头绘制变换后的 v1 和 v2
    plt.quiver([0, 0], [0, 0], 
               [v1_transformed[0], v2_transformed[0]], 
               [v1_transformed[1], v2_transformed[1]], 
               color=color_transformed, angles='xy', scale_units='xy', scale=1)
    
    # 绘制变换后向量张成的平行四边形
    plt.plot([0, v2_transformed[0], v1_transformed[0] + v2_transformed[0], v1_transformed[0]], 
             [0, v2_transformed[1], v1_transformed[1] + v2_transformed[1], v1_transformed[1]], 
             color=color_transformed)
    
    # 为变换后的 v1 添加标签，计算偏移量避免与箭头重叠
    v1_transformed_sgn = 0.04 * (vmax - vmin) * np.array([[1] if i == 0 else [i] for i in np.sign(v1_transformed)])
    ax.text(v1_transformed[0] + v1_transformed_sgn[0] - 0.1 * (1 if v1_transformed[0] < 0 else 0), 
            v1_transformed[1] - v1_transformed_sgn[1] - 0.05 * (1 if v1_transformed[1] < 0 else 0), 
            f'$T({vector_name}_1)$', fontsize=14, color=color_transformed)
    
    # 为变换后的 v2 添加标签
    v2_transformed_sgn = 0.04 * (vmax - vmin) * np.array([[1] if i == 0 else [i] for i in np.sign(v2_transformed)])
    ax.text(v2_transformed[0] + v2_transformed_sgn[0] - 0.1 * (1 if v1_transformed[0] < 0 else 0), 
            v2_transformed[1] - v2_transformed_sgn[1] - 0.05 * (1 if v1_transformed[1] < 0 else 0), 
            f'$T({vector_name}_2)$', fontsize=14, color=color_transformed)
    
    # 设置坐标轴等比例（保证圆形不被拉伸，保持真实角度）
    plt.gca().set_aspect("equal")
    
    # 显示图形
    plt.show()
    
    return fig