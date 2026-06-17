from exercise_04 import AfterError


class Result:
    __match_args__ = ("_value",)

    def __init__(self, value):
        self._value = value

    def unwrap(self):
        raise NotImplementedError()


class Ok(Result):
    def unwrap(self):
        return self._value


class Error(Result):
    def unwrap(self):
        raise self._value


import time


def after(seconds: float, func) -> Result:
    if seconds < 0:
        return Error(AfterError("Seconds must be non-negative"))
    time.sleep(seconds)
    # return Ok(...) or Error(...)
    try:
        return Ok(func())
    except Exception as err:
        return Error(err)


def test_after():
    def add(x, y):
        print(f"Adding {x} + {y} -> {x + y}")
        return x + y

    r = after(1, lambda: add(2, 3))
    assert isinstance(r, Ok), "Should have returned Ok"
    r = after(1, lambda: add(2, "three"))
    assert isinstance(r, Error), "Should have returned Error"


import math


def f(delay, value):
    r = after(delay, lambda: math.sqrt(value))
    if isinstance(r, Ok):
        print("It worked:", r.unwrap())
    elif isinstance(r, Error):
        print("It failed!")


def g(delay, value):
    match after(delay, lambda: math.sqrt(value)):
        case Ok(value):
            print("It worked:", value)
        case Error(exc):
            print(f"It failed: {exc!r}")


def h(delay, value):
    match after(delay, lambda: math.sqrt(value)):
        case Ok(value):
            print("It worked:", value)
        case Error(AfterError()) as e:  # 注意这行
            print(f"{e._value!r}")
        case Error(TypeError()):
            print("It failed: type error")
        case Error(ValueError()):
            print("It failed: bad value")
        case Error(e):
            raise e


if __name__ == "__main__":
    # test_after()
    # g(1,-1)
    ...
