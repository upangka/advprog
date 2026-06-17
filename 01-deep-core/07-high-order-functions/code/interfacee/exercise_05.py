import time


class Result:
    def __init__(self, value=None, exc=None):
        # Use value for a result produced by "return"
        # Use exc for an exception produced by "raise"
        assert (value is None) or (exc is None)
        self._value = value
        self._exc = exc

    def unwrap(self):
        # Produce the enclosed result
        if self._exc:
            raise self._exc
        else:
            return self._value


def after(seconds, func):
    # Note: This time sleep call is unguarded. If someone passes a
    # bad value, it will crash with a ValueError exception. We don't
    # worry about that.
    time.sleep(seconds)
    # You implement this. No matter what happens, we return Result.
    try:
        return Result(func())
    except Exception as err:
        return Result(exc=err)


def add(x, y):
    print(f"Adding {x} + {y} -> {x + y}")
    return x + y


def test():
    r = after(2, lambda: add(2, 3))
    assert r.unwrap() == 5

    r = after(2, lambda: add("2", 3))
    try:
        a = r.unwrap()
        print("Bad! Why did this work?")
    except TypeError as err:
        print("Good!")


test()
