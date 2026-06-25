def flatten_v1(items):
    """递归的方式"""
    for i in items:
        if isinstance(i, list):
            yield from flatten_v1(i)
        else:
            yield i


def flatten_v2(items):
    """使用stack"""
    from typing import Iterable

    # 该栈存储的是迭代器
    stack = [iter(items)]
    while stack:
        try:
            item = next(stack[-1])
            if isinstance(item, Iterable):
                stack.append(iter(item))
            else:
                yield item
        except StopIteration:
            stack.pop()


def test(f, data=[1, 2, [3, [4, 5], 6, 7], 8]):
    for i in f(data):
        print(i, end=" ")
    print()


if __name__ == "__main__":
    test(flatten_v1)
    test(flatten_v2)
