"""
- Think of messages being similar to something like email.
  There is a sender, recipient, and some kind of contents

- Sending a message is a one-way operation. When a message is
  sent, no response is returned. We don't even know if a message
  got delivered.
"""

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


"""Exercise 01 Hello World

Your first task is to try an example involving the above code.  Here
is an implementation of an actor that receives messages and simply
prints them out.
"""


class Printer(Actor):
    def handle_message(self, msg: Message):
        print(f"{msg.dest}: {msg.source} said: {msg.content}")


def printer_example():
    import time

    m = Manager()
    m.spawn("printer", Printer())
    m.send(
        Message(
            source="example",
            dest="printer",
            content="Hello World. From ShenZhen, China",
        )
    )
    time.sleep(5)
    m.send(
        Message(source="example", dest="printer", content="Are you still there World?")
    )


printer_example()
print("-" * 20)

# Question: Actors are always referenced by an address which
# is a string such as 'printer' in this example.  Is there
# a mechanism for an actor to obtain it's own address?

"""Exercise 02 Understanding the Manager

The purpose of the `Manager` class is to create a managed
environment for the `Actor` instances and to handle all of the
associated messaging.  Actors are always associated with an
enclosing Manager.  When the Manager goes away, the Actors contained
within it should also go away.  Verify that this seems to happen by
trying this example:
"""


def manager_example():
    m = Manager()
    # create a few actors
    m.spawn("广州", Printer())
    m.spawn("深圳", Printer())
    # send a few messages
    m.send(Message(source="广州", dest="深圳", content="Hi 深圳"))
    m.send(Message(source="深圳", dest="广州", content="Hi 广州"))
    # delete the manager
    # this should produce two messages about Printer actor going away
    print("About to delete manager")
    del m
    print("Manager deleted")


manager_example()
