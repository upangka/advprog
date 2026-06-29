from abc import ABC, abstractmethod

from exercise_01 import Message


class Actor(ABC):

    def __new__(cls, *args, **kwargs):
        """阻止用户实例化"""
        raise RuntimeError("Can't create instance")

    def __del__(self):
        print(f"{self} is going away")

    @abstractmethod
    def handle_message(self, message: Message): ...


class Manager:
    def __init__(self):
        """
        address (str) -> actor(Actor)
        """
        self._actors = {}

    def send(self, msg: Message):
        if msg.dest in self._actors:
            self._actors[msg.dest].handle_message(msg)

    def _get_actor(self, address: str):
        """逃生出口，方便内部测试
        Create actor via Manager, but provide an escape hatch(逃生出口)
        for getting instance for testing,debugging
        """
        return self._actors[address]

    def spawn(self, address: str, actor_cls: type[Actor], *args):
        # Python底层机制，使用父类object的__new__
        actor = object.__new__(actor_cls, *args)
        actor.__init__(*args)
        self._actors[address] = actor
        return address


class Printer(Actor):
    def __init__(self, name):
        self.name = name
        self.count = 0

    def handle_message(self, msg: Message):
        self.count += 1
        print(
            f"{self.name}[{self.count}]  From {msg.source} to {msg.dest}: {msg.content}"
        )


def test_instantiation():
    # This should fail
    try:
        p = Printer("Bob")
        assert False, "FAIL: Should not be here!!!"
    except RuntimeError as err:
        print("Good Actor")


if __name__ == "__main__":
    test_instantiation()
