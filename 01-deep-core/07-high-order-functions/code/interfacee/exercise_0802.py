import abc
import logging
import threading
import time
import uuid

logging.basicConfig(
    format="%(asctime)s - %(levelname)s - %(message)s",
    datefmt="%Y-%m-%d %H:%M:%S",
    level=logging.INFO,
)
logger = logging.getLogger(__name__)


class Result(abc.ABC):
    __match_args__ = ("_value",)

    def __init__(self, value):
        self._value = value

    @abc.abstractmethod
    def unwrap(self):
        raise NotImplementedError()


class Success(Result):
    def unwrap(self):
        return super()._value


class Fail(Result):
    def unwrap(self):
        raise super()._value


class ModernFuture:
    def __init__(self):
        self._result: Result = Success(None)
        self._evt = threading.Event()

    def set_success(self, value):
        assert not self._evt.is_set()
        self._result = Success(value)
        self._evt.set()

    def set_fail(self, exc):
        assert not self._evt.is_set()
        self._result = Fail(exc)
        self._evt.set()

    def get(self) -> Result:
        self._evt.wait()
        return self._result


def after(seconds, func, name=None) -> ModernFuture:
    fut = ModernFuture()

    id = name if name else str(uuid.uuid4())

    def run():
        time.sleep(seconds)
        try:
            fut.set_success(func())
        except Exception as err:
            fut.set_fail(err)
        finally:
            logger.info(f"{id[:8]} finished".center(50, "."))

    threading.Thread(target=run).start()
    return fut


def add(x, y):
    return x + y


def modern_example():
    logger.info("Launching functions".center(50, "."))
    f1 = after(20, lambda: add(2, 3), "f1")
    f2 = after(10, lambda: add(100, 200), "f2")
    f3 = after(5, lambda: add("two", 3), "f3")
    logger.info("Now waiting for results".center(50, "."))

    for fut, name in [(f1, "f1"), (f2, "f2"), (f3, "f3")]:
        match fut.get():
            case Success(val):
                print(f"{name} -> {val}")
            case Fail(exr):
                print(f"{name} -> {exr!r}")


if __name__ == "__main__":
    modern_example()
