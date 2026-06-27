from collections import UserDict


class udict(dict):
    def __setitem__(self, key, value):
        if isinstance(key, str):
            key = key.upper()
        super().__setitem__(key, value)


class mudict(UserDict):
    def __setitem__(self, key, value):
        key = key.upper() if isinstance(key, str) else key
        super().__setitem__(key, value)
