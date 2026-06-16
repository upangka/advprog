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


after_1(1, lambda: add(2, 3))
after_1(1, lambda: add(x=2, y=3))

after_2(1, add, (2, 3))
after_2(1, add, kwargs={'x':2, 'y':3})
# Commentary: I might be inclined to write it like this so I could
# keep the syntax of kwargs.
after_2(1, add, kwargs=dict(x=2, y=3))

after_3(1, add, 2, 3)
after_3(1, add, x=2, y=3)
