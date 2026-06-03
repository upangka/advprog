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

@dataclass
class ActorCancel(Message):
    """Special cancellation message"""
    pass

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
            try:
                self._actors[msg.dest].handle_message(msg)
                if isinstance(msg,ActorCancel):
                    raise ActorExit()
            except ActorExit as err:
                del self._actors[msg.dest]

    def spawn(self, address: str, actor: Actor):
        self._actors[address] = actor
        return address

    # You're going to implement this (and may change other parts of the code)
    def cancel(self,address: str):
        """
        We need to have some way to clearly indicate to the 
        actor that they're being cancelled. Maybe a special
        message type? None? Also, are actors allowed to 
        ignore cancel? 
        """
        self.send(ActorCancel(
                source='',
                dest=address,
                content=''))

"""Exercise 09 

Your task is to implement an approach to actor cancellation.  There are
a few requirements:

- Actors must be able to self-cancel.
- There must be a way to request cancellation from outside.
- Actors must be able to detect if they are being cancelled.

In implementing these requirements, you are *NOT* allowed to modify
the `Actor` class.  You may modify parts of the Manager.

The following example and test illustrates the requirements.
"""

class ActorExit(Exception):
    pass

class SelfCancel(Actor):
    def __init__(self,n):
        self.n = n

    def handle_message(self,msg: Message):
        if self.n <= 0:
            # Cancel self. Somehow. Raising a exception is hard to 
            # ignore. Manager could look for it. Also, exceptions
            # work well if you have a deeply nested chain of function
            # calls -- exception will propagate all the way out
            raise ActorExit()
            # assert False,"TODO"
        else:
            self.n -= 1
            print("Received: ",msg)

class OtherCancel(Actor):
    def handle_message(self,msg: Message):
        if isinstance(msg,ActorCancel):
            print('I was cancelled')
        else:
            print("Received: ",msg)


def test_cancel():
    m = Manager()
    m.spawn('self',SelfCancel(2))
    m.spawn('other',OtherCancel())
    m.send(Message(
            source="example",
            dest="self",
            content="T-minus 2"))

    m.send(Message(
            source="example",
            dest="self",
            content="T-minus 1"))

    print('self should cancel')
    m.send(Message(
            source="example",
            dest="self",
            content="T-minus 0"))
    print('Should have seen a message about SelfCancel "going away"')
    # This message should not be delivered to anything
    m.send(Message(
            source="example",
            dest="self",
            content="WHY AM I STILL ALIVE??? THIS IS A BUG!!!"))

    print("Testing other-cancel")
    m.send(Message(
            source="example",
            dest="other",
            content="Hello"))

    print("Other should cancel")
    m.cancel('other')
    print("Should haven seen a message about OtherCancel 'going away'")
    # This message should not be delivered to anything.
    m.send(Message(
            source="example",
            dest="other",
            content="WHY AM I STILL ALIVE??? THIS IS A BUG!!!"))
    print('Manager going away')
    del m

test_cancel()

