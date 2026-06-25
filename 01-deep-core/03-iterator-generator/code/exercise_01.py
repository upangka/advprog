
def countdown(n):
    print(f'Count down from {n}')
    while n > 0:
        yield n
        n -= 1


def func():
    yield 37
    return 42

