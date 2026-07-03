class ApplicationError(Exception):
    pass
def do_something():
    return int('N/A')

def spam_1():
    try:
        do_something()
    except Exception as e:
        raise ApplicationError('It failed') from e


def spam_2():
    try:
        do_something()
    except Exception as e:
        # e故意写错成eee
        raise ApplicationError('It failed') from eee
