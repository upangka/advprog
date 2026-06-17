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
    __match_args__ = "_value"

    def __init__(self, value) -> None:
        self._value = value

    def unwrap(self):
        raise NotImplementedError()

    def __rshift__(self, func):
        raise NotImplementedError()


class Ok(Result):

    def unwrap(self):
        return self._value

    def __rshift__(self, func):
        result = func(self._value)
        return Ok(result)


class Error(Result):
    def __rshift__(self, func):
        func()


x = 2
r = Ok(x) >> A >> B >> C
print(r.unwrap())  # Prints
