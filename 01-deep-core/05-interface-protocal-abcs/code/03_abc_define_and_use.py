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
    def __init__(self,items):
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
            raise LookupError('Pick from empty BingoCage')
    
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