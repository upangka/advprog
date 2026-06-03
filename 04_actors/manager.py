"""
The actor system is actually a bit more subtle than has been
described so far.  Yes, actors work by receiving and acting upon
messages.  However, in response to a message, an actor can
send messages to other actors and even spawn new actors!  Both
of these operations involve the manager.  Thus, there is
some kind of relationship between an Actor and the Manager that's
managing it.

In this project, you're going to try and solve this relation
problem.  The original actor code is copied below which you will be
required to modify.  This project does *NOT* involve any of the
changes made in Exercises 3-6 as it is addressing a separate
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


