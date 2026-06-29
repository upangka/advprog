from dataclasses import dataclass

from actors import Actor, Manager, Message


@dataclass
class Message:
    source: str
    dest: str


@dataclass
class Move(Message):
    """继承dataclass生成的__init__参数签名
    >>> import inspect
    >>> inspect.signature(Move)
    <Signature (source: str, dest: str, dx: int, dy: int) -> None>
    """

    dx: int
    dy: int


@dataclass
class Boost(Message):
    """
    <Signature (source: str, dest: str, amount: int) -> None>
    """

    amount: int


class Player(Actor):
    def __init__(self):
        self.x = 0
        self.y = 0
        self.energy = 100

    def handle_message(self, msg: Message):
        if isinstance(msg, Move):
            self.x += msg.dx
            self.y += msg.dy
            print(f"Move to: ({self.x}, {self.y})")
        elif isinstance(msg, Boost):
            self.energy += msg.amount
            print(f"Boosted to: {self.energy}")
        else:
            # Unrecognized message
            pass


def example():
    m = Manager()
    m.spawn("bob", Player())
    m.send(Move("example", "bob", 5, 10))
    m.send(Move("example", "bob", -3, 5))
    m.send(Boost("example", "bob", 25))
    del m


if __name__ == "__main__":
    example()
