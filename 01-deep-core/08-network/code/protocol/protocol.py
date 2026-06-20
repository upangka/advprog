import json


class Message:
    _registry = {}

    def __init_subclass__(cls) -> None:
        print(f"Initing {cls.__name__}")
        Message._registry[cls.__name__] = cls

        annotations = cls.__init__.__annotations__

        # monkey pathging
        @classmethod
        def from_untrust(cls, **kwargs):
            for key, value in kwargs.items():
                if not isinstance(value, annotations[key]):
                    raise TypeError(
                        f"{kwargs[key]} is {type(kwargs[key]).__name__} expect {cls.__name__}"
                    )
            return cls(**kwargs)

        cls.from_untrust = from_untrust

    def __eq__(self, other) -> bool:
        return type(self) == type(other) and vars(self) == vars(other)

    def __repr__(self) -> str:
        return f"{type(self).__name__}<{repr(vars(self))}>"


class ChatMessage(Message):
    def __init__(self, playid: str, text: str):
        self.playid = playid
        self.text = text


class PlayerUpdate(Message):
    def __init__(self, playerid: str, x: int, y: int):
        self.playerid = playerid
        self.x = x
        self.y = y


def encode_message(msg: Message) -> bytes:
    msgtype = type(msg).__name__.encode("utf-8") + b"\r\n"
    payload = json.dumps(msg.__dict__).encode("utf-8")
    size = str(len(payload)).encode("utf-8") + b"\r\n"
    return msgtype + size + payload


def example():
    msg1 = ChatMessage("鲨鱼のJavthon", "Hello World")
    msg2 = PlayerUpdate("Pkmer", 23, 56)
    print(encode_message(msg1))
    print(encode_message(msg2))


if __name__ == "__main__":
    example()
