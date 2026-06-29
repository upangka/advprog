import abc
from dataclasses import dataclass


@dataclass
class Message:
    source: str
    dest: str
    content: str


class Actor(abc.ABC):
    """Actors are objects that receive and respond to messages"""

    def __del__(self):
        print(f"{self} is going way")

    @abc.abstractmethod
    def handle_message(self): ...


class Manager:
    """
    - The Manager provides a runtime enviroment for actors
    - Everything goes way when the Manager goes away
    """

    def __init__(self):
        # address: str -> actor: Actor:
        self._actors = {}

    def send(self, msg: Message):
        if msg.dest in self._actors:
            self._actors[msg.dest].handle_message(msg)

    def spawn(self, address: str, actor: Actor):
        self._actors[address] = actor
        return address
