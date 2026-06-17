import logging
import threading
import time
import uuid

logging.basicConfig(
    format="%(asctime)s - %(levelname)s - %(message)s",
    datefmt="%H:%M:%S",
    level=logging.INFO,
)
logger = logging.getLogger(__name__)


"""
Part-1
"""


class Future:
    def __init__(self):
        self._value = None
        self._exc = None
        self._evt = threading.Event()

    def set_value(self, value):
        self._value = value
        self._evt.set()

    def set_exception(self, exc):
        self._exc = exc
        self._evt.set()

    def result(self):
        self._evt.wait()
        if self._exc is None:
            return self._value
        else:
            raise self._exc


def after(seconds, func, name=None):
    fut = Future()

    id = name if name else str(uuid.uuid4())

    def run():
        time.sleep(seconds)
        try:
            fut.set_value(func())
        except Exception as err:
            fut.set_exception(err)
        finally:
            logger.info(f"{id[:8]} finished".center(50, "."))

    threading.Thread(target=run).start()
    return fut


def add(x, y):
    return x + y


def example():
    logger.info("Launching functions".center(50, "."))
    f1 = after(20, lambda: add(2, 3), "f1")
    f2 = after(10, lambda: add(100, 200), "f2")
    f3 = after(5, lambda: add("two", 3), "f3")
    logger.info("Now waiting for results".center(50, "."))
    print(f"f1 -> {f1.result()}")
    print(f"f2 -> {f2.result()}")
    try:
        f3.result()
    except TypeError as err:
        logger.error(f"f3 -> {err}")


if __name__ == "__main__":
    example()
