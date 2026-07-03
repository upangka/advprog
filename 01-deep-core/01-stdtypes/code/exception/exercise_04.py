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
        raise ApplicationError(1, "It failed") from None
        # raise ApplicationError(1,"It failed")


def main():
    try:
        spam()
    except ApplicationError as err:
        print("It's Failed. Reason: ", err.__cause__)
        print("__context__",type(err.__context__),err.__context__)
        print("__suppress_context__",err.__suppress_context__)


if __name__ == "__main__":
    main()
