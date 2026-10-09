"""
1. Loss 是“信号”，它通过 backward() 把梯度存到参数的 .grad 里。
2. Optimizer 是“执行者”，它通过 step() 读取 .grad 并改参数的值。
3. 它们通过模型参数本身这个共享内存联系在了一起。

标准三步曲：清零 -> 反向 -> 更新（zero_grad -> backward -> step）
"""



import torch

if __name__ == '__main__':
    pass