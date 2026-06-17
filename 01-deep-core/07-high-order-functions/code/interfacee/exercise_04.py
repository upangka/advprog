from after import after
import math
import time


def f(delay, value):
    try:
        value = after(delay, lambda: math.sqrt(value))
        print("It worked:", value)
    except ValueError as err:
        print("It failed")


class AfterError(Exception):
    pass


def after_1(seconds, func):
    if seconds < 0:
        raise AfterError("Seconds must be non-negative")
    time.sleep(seconds)
    return func()


def after_2(seconds, func):
    time.sleep(seconds)
    try:
        return func()
    except Exception as err:
        raise AfterError("Function failed") from err


def after(seconds, func):
    if seconds < 0:
        raise AfterError("Seconds must be non-negative")
    if not callable(func):
        raise AfterError("func must be a callable")
    time.sleep(seconds)
    return func()
