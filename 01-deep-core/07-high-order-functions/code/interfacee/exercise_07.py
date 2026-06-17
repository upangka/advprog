from __future__ import annotations

import time


def A(x: int) -> int:
    print("running A...")
    return x + 10


def B(x: int) -> int:
    print("running B...")
    return x * 2


def C(x: int) -> int:
    print("running C...")
    return x * x


def chained_after(x: int) -> int:
    from exercise_06 import Error, Ok, Result, after

    a = after(1, lambda: A(x))  # Call a = A(x) after 1 second    (must modify)
    b = after(
        2, lambda: B(a.unwrap())
    )  # Call b = B(a), 2 seconds after that (must modify)
    c = after(
        3, lambda: C(b.unwrap())
    )  # Call c = C(b), 3 seconds after that (must modify)
    return c.unwrap()


# assert chained_after(2) == 576  # Uncomment


class Result:
    __match_args__ = ("_value",)

    def __init__(self, value) -> None:
        self._value = value

    def unwrap(self):
        raise NotImplementedError()

    def __rshift__(self, func) -> Result:
        raise NotImplementedError()

    def __repr__(self) -> str:
        return f"{self._value!r}"


class Ok(Result):

    def unwrap(self):
        return self._value

    def __rshift__(self, func) -> Result:
        """
        self >> func
        Since we are an 'Ok' instance, it means that we hold a good value.
        We will pass it into the function. But we have to be mindful
        that it might fail
        """
        try:
            return Ok(func(self._value))
        except Exception as err:
            return Error(err)


class Error(Result):
    def unwrap(self):
        raise self._value

    def __rshift__(self, func) -> Result:
        # self >> func
        return self  # I'm an error. I stay an error.


def test_chain_operator(x):
    x = 2
    r = Ok(x) >> A >> B >> C
    print(r.unwrap())  # Prints
    assert r.unwrap() == 576

    match Ok(x) >> A >> B >> C:
        case Ok(value):
            print("It worked:", value)
            assert value == 576
        case Error(e):
            print(f"It failed: {e!r}")

    match Ok("two") >> A >> B >> C:
        case Ok(value):
            raise AssertionError("Why am I here?")
        case Error(e):
            print(isinstance(e, TypeError))
            assert isinstance(e, TypeError)


def after(seconds, func):
    def run(*args, **kwargs):
        time.sleep(seconds)
        return func(*args, **kwargs)

    return run


def test_after():
    r = Ok(2) >> after(1, A) >> B >> after(3, C)
    print(r)
    assert r._value == 576
