class ApplicationError(Exception):
    def __init__(self, errno, msg):
        self.args = (errno, msg)
        self.errno = errno
        self.msg = msg


def do_something():
    x = int("N/A")


def spam():
    try:
        do_something()
    except ValueError as err:
        # raise from 将None封装到ApplicationError的__cause__属性
        # 但是注意仍然能够通过__context__属性访问到原始异常
        # raise ApplicationError(1, "It failed") from None
        raise ApplicationError(1, "It failed")


def main():
    import traceback

    try:
        spam()
    except ApplicationError as err:
        tblines = traceback.format_exception(type(err), err, err.__traceback__)
        for i, v in enumerate(tblines, 1):
            print(i, "=>", v)


if __name__ == "__main__":
    main()
