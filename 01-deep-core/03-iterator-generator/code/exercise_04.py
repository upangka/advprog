
def countup(stop):
    n = 1
    while n <= stop:
        yield n
        n += 1

def countdown(start):
    n = start
    while n > 0:
        yield n
        n -= 1


def up_and_down_v1(n):
    for x in countup(n):
        yield x
    for x in countdown(n):
        yield x

def up_and_down_v2(n):
    """相当于语法糖，简化写法"""
    yield from countup(n)
    yield from countdown(n)
