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

```txt
欢迎使用输入法模型(输入q或者quit退出)
> 我们
输入历史: 我们
Building prefix dict from the default dictionary ...
Loading model from cache /tmp/jieba.cache
Loading model cost 0.615 seconds.
Prefix dict has been built successfully.
预测结果: ['的', '可以', '团队', '都', '一起']
> 团队
输入历史: 我们团队
预测结果: ['做', '合作', '一起', '协作', '开发']
> 一起
输入历史: 我们团队一起
预测结果: ['努力', '加油', '去', '了', '做']
> 做
输入历史: 我们团队一起做
预测结果: ['一个', '了', '项目', '一些', '个']
> 项目
输入历史: 我们团队一起做项目
预测结果: ['，', '时', '。', '就', '边学']
> 。
输入历史: 我们团队一起做项目。
预测结果: ['你', '不过', '比如', '加油', '我们']
```
