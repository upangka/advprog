import time


def after_1(seconds, func):
    time.sleep(seconds)
    return func()


def after_2(seconds, func, args=(), kwargs={}):
    time.sleep(seconds)
    return func(*args, **kwargs)


def after_3(seconds, func, *args, **kwargs):
    time.sleep(seconds)
    return func(*args, **kwargs)


def add(x, y):  # You are NOT allowed to change this function
    print(f"Adding {x} + {y} -> {x + y}")
    return x + y


def part_1():
    after_1(1, lambda: add(2, 3))
    after_1(1, lambda: add(x=2, y=3))

    after_2(1, add, (2, 3))
    after_2(1, add, kwargs={"x": 2, "y": 3})
    # Commentary: I might be inclined to write it like this so I could
    # keep the syntax of kwargs.
    after_2(1, add, kwargs=dict(x=2, y=3))

    after_3(1, add, 2, 3)
    after_3(1, add, x=2, y=3)


def part_2():
    # after_1(1, lambda: after_1(1, lambda: add(2, 3)))
    # after_1(1, lambda: after_1(seconds=1, func=lambda: add(2, 3)))

    # after_3(1, lambda: after_3(1, add, 2, 3))
    # after_3(1, lambda: after_3(seconds=1, func=add, **dict(x=2, y=3)))
    # after_3(1, lambda: after_3(seconds=1, func=add, x=2, y=3))

    # after_2(1, lambda: after_2(1, add, args=(2, 3)))
    # after_2(1, lambda: after_2(seconds=1, func=add, kwargs=dict(x=2, y=3)))

    after_2(1, after_2, args=(1, add, (2, 3)))
    # 直接用字段全部代替
    after_2(1, after_2, kwargs=dict(seconds=1, func=add, args=(2, 3)))
    # kwargs需要多处理一层
    after_2(1, after_2, args=(1, add), kwargs={"kwargs": dict(x=2, y=3)})

    after_3(1, after_3, 1, add, 2, 3)
    after_3(1, after_3, 1, add, x=2, y=3)
