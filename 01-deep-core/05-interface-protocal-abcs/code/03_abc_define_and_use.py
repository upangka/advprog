import abc
from typing import Any


class Tombola(abc.ABC):

    @abc.abstractmethod
    def load(self, iterable):
        """Add items from an iterable"""

    @abc.abstractmethod
    def pick(self):
        """Remove item at random, returning it
        This method should raise `LookupError` when the instance is empty
        """

    def loaded(self):
        """Return `True` if there's at least 1 item, `False` otherwise"""
        return bool(self.inspect())

    def inspect(self):
        """Return a sorted tuple with the items currently inside"""
        items = []

        while True:
            try:
                items.append(self.pick())
            except LookupError:
                break

        self.load(items)  # 恢复数据
        return tuple(items)


import random


class BingoCage(Tombola):
    def __init__(self, items):
        self._randomizer = random.SystemRandom()
        self._items = []
        self.load(items)

    def load(self, iterable):
        self._items.extend(iterable)
        self._randomizer.shuffle(self._items)

    def pick(self):
        try:
            return self._items.pop()
        except IndexError:
            raise LookupError("Pick from empty BingoCage")

    def __call__(self):
        return self.pick()

    def __getitem__(self, key):
        """
        迭代器测试
        Examples:
        >>> a = BingoCage([1,2,3,])
        >>> list(a)
        [2, 1, 3]
        >>> list(a)
        []
        """
        try:
            return self()
        except LookupError:
            raise StopIteration


class LottoBlower(Tombola):
    def __init__(self, iterable) -> None:
        self._balls = list(iterable)

    def load(self, iterable):
        self._balls.extend(iterable)

    def pick(self):
        try:
            pos = random.randrange(len(self._balls))
        except ValueError:
            raise LookupError("Pick from empty LottoBlower")
        return self._balls[pos]

    def loaded(self):
        """覆盖父类笨重的方法"""
        return bool(self._balls)

    def inspect(self):
        return tuple(self._balls)


@Tombola.register
class TomboList(list):
    # 这个方法复用这是优秀
    load = list.extend

    def pick(self):
        if self:
            pos = random.randrange(len(self))
            return self.pop(pos)
        else:
            raise LookupError("Pop from empty TomboList")

    def loaded(self):
        return bool(self)

    def inspect(self):
        return tuple(self)


def static_checker(t: Tombola):
    print(f"{type(t)} {issubclass(type(t),Tombola)}")


static_checker(BingoCage("123"))  # <class '__main__.BingoCage'> True
static_checker(LottoBlower("123"))  # <class '__main__.LottoBlower'> True

# static_checker(TomboList())
