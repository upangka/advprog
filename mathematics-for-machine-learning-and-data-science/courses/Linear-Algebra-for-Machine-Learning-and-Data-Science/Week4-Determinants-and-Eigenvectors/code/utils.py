import numpy as np
import matplotlib.pyplot as plt

def plot_transformation(T, v1, v2, vector_name='v'):
    """
    绘制线性变换 T 对两个基向量 v1, v2 的作用效果
    
    参数:
        T: 线性变换（矩阵或函数）
        v1: 第一个基向量
        v2: 第二个基向量
        vector_name: 向量名称（用于图例标签）
    
    返回:
        fig: matplotlib 图形对象
    """
    
    color_original = "#129cab"
    color_transformed = "#cc8933"
    
    v1_transformed = T @ v1
    v2_transformed = T @ v2
    
    fig, ax = plt.subplots(figsize=(7, 7))
    ax.tick_params(axis='x', labelsize=14)
    ax.tick_params(axis='y', labelsize=14)
    
    # ===== 修复：使用 .real 只取实部，避免复数干扰 =====
    all_vectors = np.concatenate([
        v1.flatten().real,
        v2.flatten().real,
        v1_transformed.flatten().real,
        v2_transformed.flatten().real,
        (v1_transformed + v2_transformed).flatten().real
    ])
    
    vmin = np.floor(np.min(all_vectors) - 0.5)
    vmax = np.ceil(np.max(all_vectors) + 0.5)
    
    ax.set_xticks(np.arange(vmin, vmax))
    ax.set_yticks(np.arange(vmin, vmax))
    plt.axis([vmin, vmax, vmin, vmax])
    
    # === 绘制原始向量 ===
    plt.quiver([0, 0], [0, 0], 
               [v1[0].real, v2[0].real], 
               [v1[1].real, v2[1].real], 
               color=color_original, angles='xy', scale_units='xy', scale=1)
    
    plt.plot([0, v2[0].real, (v1[0] + v2[0]).real, v1[0].real], 
             [0, v2[1].real, (v1[1] + v2[1]).real, v1[1].real], 
             color=color_original)
    
    # 为 v1 添加标签
    v1_sgn = (0.02 * (vmax - vmin) * np.sign(v1).flatten().real).tolist()
    ax.text(v1[0].real + v1_sgn[0], v1[1].real, f'${vector_name}_1$', 
            fontsize=14, color=color_original)
    
    # 为 v2 添加标签
    v2_sgn = (0.02 * (vmax - vmin) * np.sign(v2).flatten().real).tolist()
    ax.text(v2[0].real, v2[1].real + v2_sgn[1], f'${vector_name}_2$', 
            fontsize=14, color=color_original)
    
    # === 绘制变换后的向量 ===
    plt.quiver([0, 0], [0, 0], 
               [v1_transformed[0].real, v2_transformed[0].real], 
               [v1_transformed[1].real, v2_transformed[1].real], 
               color=color_transformed, angles='xy', scale_units='xy', scale=1)
    
    plt.plot([0, v2_transformed[0].real, (v1_transformed[0] + v2_transformed[0]).real, v1_transformed[0].real], 
             [0, v2_transformed[1].real, (v1_transformed[1] + v2_transformed[1]).real, v1_transformed[1].real], 
             color=color_transformed)
    
    # 为变换后的 v1 添加标签
    v1_transformed_sgn = (0.04 * (vmax - vmin) * np.sign(v1_transformed).flatten().real).tolist()
    ax.text(v1_transformed[0].real + v1_transformed_sgn[0] - 0.1 * (1 if v1_transformed[0].real < 0 else 0), 
            v1_transformed[1].real - v1_transformed_sgn[1] - 0.05 * (1 if v1_transformed[1].real < 0 else 0), 
            f'$T({vector_name}_1)$', fontsize=14, color=color_transformed)
    
    # 为变换后的 v2 添加标签
    v2_transformed_sgn = (0.04 * (vmax - vmin) * np.sign(v2_transformed).flatten().real).tolist()
    ax.text(v2_transformed[0].real + v2_transformed_sgn[0] - 0.1 * (1 if v2_transformed[0].real < 0 else 0), 
            v2_transformed[1].real - v2_transformed_sgn[1] - 0.05 * (1 if v2_transformed[1].real < 0 else 0), 
            f'$T({vector_name}_2)$', fontsize=14, color=color_transformed)
    
    plt.gca().set_aspect("equal")
    plt.show()
    
    return fig