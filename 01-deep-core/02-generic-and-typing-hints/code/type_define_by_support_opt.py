from collections.abc import Sequence

# 这里声明为Sequence类型，但是它不支持__mul__操作


def double(x: Sequence):
    return x * 2
