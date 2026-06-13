from collections import abc
class Struggle:
    def __len__(self):
        return 9
    
def handle_sizeobj(s: abc.Sized):
    ...
    
handle_sizeobj(Struggle())


import abc
class MySized(abc.ABC):
    @classmethod
    def __subclasshook__(cls,C):
        print(C.__mro__)
        if cls is MySized:
            if any("__len__" in B.__dict__ for B in C.__mro__):
                print(f"检测到 `__len__` 存在 f{C}")
                return True
        return NotImplemented
