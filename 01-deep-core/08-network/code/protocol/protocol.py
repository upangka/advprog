import json


class Message:
    def __eq__(self, other) -> bool:
        return type(self) == type(other) and vars(self) == vars(other)

    def __repr__(self) -> str:
        return f"{type(self).__name__}<{repr(vars(self))}>"


class ChatMessage(Message):
    def __init__(self, playerid: str, text: str):
        self.playid = playerid
        self.text = text


class PlayerUpdate(Message):
    def __init__(self, playid: str, x: int, y: int):
        self.playid = playid
        self.x = x
        self.y = y
