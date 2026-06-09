# --- This code is copied directly from actors.py

from dataclasses import dataclass


@dataclass
class Message:
    source: str
    dest: str
    content: str


class Actor:
    """
    - Actors are objects that receive and respond to messages
    """

    def __del__(self):
        print(f"{self} is going away")

    def handle_message(self):
        raise NotImplementedError("Actors must implement handle_message()")


class Manager:
    """
    - The Manager provides a runtime enviroment for actors
    - Everything goes way when the Manager goes away
    """

    def __init__(self):
        self._actors = {}

    def send(self, msg: Message):
        if msg.dest in self._actors:
            self._actors[msg.dest].handle_message(msg)

    def spawn(self, address: str, actor: Actor):
        self._actors[address] = actor
        return address


"""
A key part of the actor system is the concept of a message.  A
message minimally contains information about the sender and
recipient.  This is encoded in the `source` and `dest` fields.
However, a message also contains some kind of content that is to be
interpreted by the receiver.

A debate has erupted about the interpretation of the content.  As
currently written, the content is encoded as a string.  Any
interpretation is based on string processing.  The following code
shows an example:
"""


class Player(Actor):
    def __init__(self):
        self.x = 0
        self.y = 0
        self.energy = 100

    def handle_message(self, msg: Message):
        parts = msg.content.split()
        if parts[0] == "move":
            self.x += int(parts[1])
            self.y += int(parts[2])
            print(f"Move to: ({self.x},{self.y})")
        elif parts[0] == "boost":
            self.energy += int(parts[1])
            print(f"Boosted to: {self.energy}")
        else:
            # Unrecognized message
            pass


def old_example():
    m = Manager()
    m.spawn("bob", Player())
    m.send(Message("example", "bob", "move 5 10"))  # <<<< I want more structure on this
    m.send(Message("example", "bob", "move -3 5"))
    m.send(Message("example", "bob", "boost 25"))
    del m


# old_example()
"""Exercise 03

It has been argued that this particular design for messages is
both slow and error-prone.  Consider:

- The string content is completely unstructured.  Literally
anything could be encoded inside.  So, we have to write code to
parse and validate it.

- There are many possible message variants in the system.  For
example, the code above recognizes "move" and "boost" messages,
but there could be many more added later.

- The message handling code in handle_message() is performing
some kind of case-analysis with an if-elif-else statement to
figure out what the message is.  This seems like it could be slow.

Eva has suggested that the different message types could possibly
be represented by their own classes.  For example:

    @dataclass
    class Move:
        dx : int
        dy : int

    @dataclass
    class Boost:
        amount : int

However, it's somewhat unclear how such an approach might
integrate with the already existing design.

Your task: Figure out a better design for messages that allows for
many different message variants to be supported as well as more
efficient message dispatching in the `handle_message()` method of
actors.

You can modify all relevant parts of this file to support
your proposed redesign including the above example.

There's not really a "right" answer to this problem, but you should
be prepared to justify your approach.  Why did you choose to
organize the code in "that" way?  Note: parts of the problem
involve performance.  You might try to make some performance
measurements to justify your choices.
"""


# One Approach: Define Message variants via inheritance


@dataclass
class Message:
    source: str
    dest: str


@dataclass
class Move(Message):
    dx: int
    dy: int


@dataclass
class Boost(Message):
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
            print(f"Move to: ({self.x},{self.y})")
        elif isinstance(msg, Boost):
            self.energy += msg.amount
            print(f"Boosted to: {self.energy}")
        else:
            # Unrecognized message
            pass


def example():
    m = Manager()
    m.spawn("bob", Player())
    m.send(Move(source="example", dest="bob", dx=5, dy=10))

    m.send(Move(source="example", dest="bob", dx=-3, dy=5))

    m.send(Boost(source="example", dest="bob", amount=25))
    del m


example()
