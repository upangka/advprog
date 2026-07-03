def demo1():
    """待优化的代码"""
    n = 100
    found = False

    for a in range(n):
        if found:
            break
        for b in range(n):
            if found:
                break
            for c in range(n):
                if 42 * a + 17 * b + c == 5096:
                    found = True
                    print("42 * a + 17 * b + c = 5096")
                    print(f"{a=} {b=} {c=}")


class ExitLoopError(Exception):
    def __init__(self, *, a: int, b: int, c: int):
        super().__init__(f"{a=} {b=} {c=}")
        self.a = a
        self.b = b
        self.c = c


def demo2():
    """
    使用异常处理流程控制语句
    """
    n = 100
    found = False

    try:
        for a in range(n):
            for b in range(n):
                for c in range(n):
                    if 42 * a + 17 * b + c == 5096:
                        raise ExitLoopError(a=a, b=b, c=c)
    except ExitLoopError as err:
        print("42 * a + 17 * b + c = 5096")
        a, b, c = err.a, err.b, err.c
        # args 已经被我组织成了字符串a= b= c=的形式
        print(err)  # a=79 b=99 c=95
        print(*err.args)  # a=79 b=99 c=95


def demo3():
    """借助内置模块itertools.product
    Equivalent to nested for-loops
    """
    import itertools

    for a, b, c in itertools.product(range(100), repeat=3):
        if 42 * a + 17 * b + c == 5096:
            print("42 * a + 17 * b + c = 5096")
            print(f"{a=} {b=} {c=}")
