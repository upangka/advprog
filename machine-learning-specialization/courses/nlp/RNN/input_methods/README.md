[HundredCV-Chat](https://huggingface.co/datasets/Jax-dan/HundredCV-Chat/tree/main)

```python
input_method
├── data                    # 数据目录
│   ├── processed           # 预处理后的数据
│   └── raw                 # 原始数据
├── logs                    # 训练日志
├── models                  # 保存训练好的模型参数
└── src                     # 源码目录
    ├── config.py           # 超参数配置
    ├── dataset.py          # 自定义Dataset
    ├── evaluate.py         # 模型评估脚本
    ├── model.py            # 模型结构定义
    ├── predict.py          # 模型推理脚本
    ├── process.py          # 数据预处理脚本
    ├── tokenizer.py        # 自定义分词器
    └── train.py            # 模型训练脚本
```

# 核心

## 预处理

1. 构建词表
2. 构建训练集
3. 构建测试集

# tensorboard

```pyt
uv run tensorboard --logdir ./RNN/input_methods/logs
```
