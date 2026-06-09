from __future__ import annotations

"""
Throughout this project, we've experimented with different
implementation issues and made various code modifications to address
those issues.  Your final task is to take everything you've learned
so far and to create a final implementation of the Actor system
that incorporates everything all at once.

Just to recall, this includes:

1. A way to have message variants can be processed quickly
   and reliably.

2. No direct instantiation of actors.  Actors can only be
   referenced by their address.

3. Actors must be able to send messages and spawn new actors
   in response to receiving a message.

4. Actors must be able to self cancel.  Cancellation requests
   from the outside must also be honored.
"""

from dataclasses import dataclass, field
from typing import Optional


@dataclass
class Message:
    dest: str
    source: str = field(default="")
    content: str = field(default="")


@dataclass
class Move(Message):
    dx: int = 0
    dy: int = 0


class ActorExit(Exception):
    pass


class Actor:

    def __new__(self, *args, **kwargs):
        raise RuntimeError("Don't allow to instance")

    def __init__(self, name: Optional[str] = None):
        self.name = name

    def __del__(self):
        print(f"{type(self).__name__}[{self.name}] going away")

    def handle_message(self, msg: Message, manager: Manager):
        raise NotImplementedError("Require to implement")


class Manager:
    def __init__(self):
        self._actors = {}

    def send(self, msg: Message):
        if msg.dest in self._actors:
            try:
                self._actors[msg.dest].handle_message(msg, self)
                if isinstance(msg, ActorCancel):
                    raise ActorExit()
            except ActorExit as err:
                del self._actors[msg.dest]

    def spawn(self, address: str, actorcls: type[Actor], *args):
        actor = object.__new__(actorcls)
        actor.__init__(*args)
        self._actors[address] = actor
        return address

    def cancel(self, address: str):
        del self._actors[address]


# ---------------------------------------------------------------


class ActorCancel(Message):
    pass


class DisplayActor(Message):
    pass


class Printer(Actor):
    def handle_message(self, msg: Message, manager: Manager):
        print(f"[{self.name}] show: {msg.content}")


class PlayerFactory(Actor):
    def handle_message(self, msg: Message, manager: Manager):
        manager.spawn(msg.content, Player, "Pkmer")


class Player(Actor):

    def __init__(self, name):
        self.x = 0
        self.y = 0
        super().__init__(name)

    def handle_message(self, msg: Message, manager: Manager):
        if isinstance(msg, Move):
            self.x += msg.dx
            self.y += msg.dy
        elif isinstance(msg, ActorCancel):
            print(f"{self.name} will to exit")
            raise ActorExit()
        elif isinstance(msg, DisplayActor):
            manager.send(
                Message(
                    source=self.name,
                    dest=msg.content,
                    content=f"Player[{self.name}]({self.x},{self.y})",
                )
            )
        else:
            # Ignore other messages
            pass


if __name__ == "__main__":
    m = Manager()
    m.spawn("player-factory", PlayerFactory)
    m.spawn("printer", Printer, "win-printer")

    # create player actor
    m.send(Message(source="example", dest="player-factory", content="p1"))

    # test player actor
    m.send(Move(source="example", dest="p1", dx=3, dy=10))

    m.send(Move(source="example", dest="p1", dx=2, dy=15))

    m.send(DisplayActor(dest="p1", content="printer"))
    m.cancel("printer")
    # should not see the printer show player information
    m.send(DisplayActor(dest="p1", content="printer"))

    # create another new printer
    m.spawn("new-printer", Printer, "mac-printer")
    m.send(
        Message(source="", dest="new-printer", content="Love From ShenZhen, China :)")
    )
    del m
