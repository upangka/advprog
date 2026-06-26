class A:
    def __repr__(self) -> str:
        return f"{type(self).__name__} ..."

    __str__ = __repr__


class B(A):
    pass


if __name__ == "__main__":
    print(A())
    print(B())
