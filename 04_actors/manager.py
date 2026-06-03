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


"""Exercise 07 The Sender

Arjun has been trying to solve the problem of having an actor send a
message to another actor.  It is not going well.  The code he's written
so far is included below.

Your task: Modify the Actor and Manager classes so that it is possible
for an Actor to send messages and spawn new actors.  Then, modify his
example code so that it works as desired.
"""

# An Actor that prints
class Printer(Actor):
    def handle_message(self,msg: Message):
        print(f"{msg.dest}: {msg.source} said: {msg.content}")

# An actor that counts up/down
class Counter(Actor):

    def __init__(self):
        self.count = 0

    def handle_message(self,msg: Message):
        if msg.content == 'up':
            self.count += 1
        elif msg.content == 'down':
            self.count -= 1
        elif msg.content == 'display':
            # Stuck. How do I make this work?
            send(Message(
                    dest="printer",
                    source=msg.source,
                    content=str(self.count)
                ))


# An actor that creates Counter Actor
class CounterFactory(Actor):
    def handle_message(self,msg: Message):
        # Create a new Counter. But how???
        spawn(msg.content,Counter())

def send_example():
    m = Manager()

    # Create two actors
    m.spawn("printer",Printer())
    m.spawn("factory",CounterFactory())

    # Create a counter c1 via the factory
    m.send(Message(
            source="example",
            dest="factory",
            content="c1"
        ))

    # Send the newly created counter c1 some messages
    m.send(Message(
            source="example",
            dest="c1",
            content="up"
        ))
    m.send(Message(
            source="example",
            dest="c1",
            content="up"
        ))

    m.send(Message(
            source="example",
            dest="c1",
            content="down"
        ))
    
    print("You should see the printer produce an output of '1' below.")
    m.send(Message(
            source="example",
            dest="c1",
            content="display"
        ))

    print("Deleting the manager. All of the actors should go away now")
    del m
    print("You should have seen three 'going away' messages above.")



































