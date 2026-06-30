from __future__ import annotations

import abc
from typing import Any, ClassVar


class BaseDecoder(abc.ABC):

    _registry: ClassVar[dict[str, type[BaseDecoder]]] = {}

    mimetypes: ClassVar[list[str]]

    def __init_subclass__(cls):
        if "mimetypes" not in vars(cls):
            raise TypeError(f"{cls.__name__} must define 'mimetypes' class variable")

        BaseDecoder._registry.update({mt: cls for mt in cls.mimetypes})

    @abc.abstractmethod
    def decode(self, data): ...


class TextDecoder(BaseDecoder):
    mimetypes = ["text/plain"]

    def decode(self, data):
        pass


class HTMLDecoder(BaseDecoder):
    mimetypes = ["text/html"]

    def decode(self, data):
        pass


class ImageDecoder(BaseDecoder):
    mimetypes = ["image/png", "image/jpg", "image/gif"]

    def decode(self, data):
        pass

if __name__ == '__main__':
    from pprint import pprint
    pprint(BaseDecoder._registry)
