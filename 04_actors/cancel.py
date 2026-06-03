"""
As currently implemented, actors can be created, but there
is no way for an actor to terminate and go away.  Your
task in this project is to formulate some kind of approach
to actor cancellation.  However, there's a twist.

In our actor system, the *ONLY* operation allowed on an actor is the
act of sending it a message.  You are *NOT* allowed to add new
methods to the Actor class.

To start, code from `actors.py` is copied below.  As with other
exercises, you may be modifying parts of this code.  You
are not using code from earlier exercises as this is a separate
concern.
"""

from dataclasses import dataclass


@dataclass
class Message:
    source: str
    dest: str
    content: str


class Actor:

    def __del__(self):
        print(f"{self} is going away")

    def handle_message(self):
        raise NotImplementedError('Actors must implement handle_message()')


class Manager:

    def __init__(self):
        self._actors = {}

    def send(self, msg: Message):
        if msg.dest in self._actors:
            self._actors[msg.dest].handle_message(msg)

    def spawn(self, address: str, actor: Actor):
        self._actors[address] = actor
        return address


