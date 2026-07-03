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
        # raise from 将err封装到ApplicationError的__cause__属性
        raise ApplicationError(1, "It failed") from err


def main():
    try:
        spam()
    except ApplicationError as err:
        print("It's Failed. Reason: ", err.__cause__)


if __name__ == "__main__":
    main()
