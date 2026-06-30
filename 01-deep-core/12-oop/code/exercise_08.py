_registry = {}


def register_decoder(cls):
    _registry.update({mt: cls for mt in cls.mimetypes})
    # 类装饰器要返回类，良好习惯
    return cls


def create_decoder(mimetype):
    if mimetype not in _registry:
        raise RuntimeError(f"Please regist {mimetype}")
    return _registry[mimetype]()


@register_decoder
class TextDecoder:
    mimetypes = ["text/plain"]

    def decode(self, data):
        pass


@register_decoder
class HTMLDecoder:
    mimetypes = ["text/html"]

    def decode(self, data):
        pass


@register_decoder
class ImageDecoder:
    mimetypes = ["image/png", "image/jpg", "image/gif"]

    def decode(self, data):
        pass


def test_register_decoder():
    from pprint import pprint

    # 已经全部注册进去了
    pprint(_registry)
    decoder = create_decoder("image/gif")
    assert isinstance(decoder, ImageDecoder), "Create ImageDecoder Failed!!!"
    print("Good test")


if __name__ == "__main__":
    test_register_decoder()

"""output
{'image/gif': <class '__main__.ImageDecoder'>,
 'image/jpg': <class '__main__.ImageDecoder'>,
 'image/png': <class '__main__.ImageDecoder'>,
 'text/html': <class '__main__.HTMLDecoder'>,
 'text/plain': <class '__main__.TextDecoder'>}
"""
