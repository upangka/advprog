"""

Actors are a lot like objects in that they have internal state and
they respond to received messages (which are analogous to methods).
However, an important feature of actors is that they are only
referenced by their address.  In our system, these addresses are
encoded as strings such as "bob" or "alice".

Encapsulation is a critical part of the system.  The internal
state of an Actor is never meant to be exposed or accessed in
any way.  Again, the *ONLY* allowed operation is sending a message.
Much of this is handled by the Manager.  All actors live inside
an environment created by the manager.

In this exercise, your goal is to strongly enforce these
encapsulation rules in the implementation.  Basically making it
impossible (or at least rather difficult) to violate the desired
rules of encapsulation and usage.

We start with a copy of the original code from `actors.py` which is
included below.  Note: Please ignore Exercise 3 (message.py) for
this exercise.  This is addressing a separate concern.
"""

# --- This code is copied directly from actors.py
from dataclasses import dataclass


@dataclass
class Message:
    source: str
    dest: str
    content: str


class Actor:
    
    # One thought: If I want to make it impossible to create Actors,
    # I could break  `__new__`
    
    def __new__(cls, *args, **kwargs):
        raise RuntimeError("Can't create instances")



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


# An Example actor

class Printer(Actor):
    def __init__(self,name):
        self.name = name
        self.count = 0

    def handle_message(self, msg: Message):
        self.count += 1
        print(f"{self.name}[{self.count}]: {msg.source} said: {msg.content}")



"""Exercise 04  Preventing Direct Instantiation

The only allowed way to refer to an Actor is by its address--a string.  One
way to circumvent this would be to create an Actor instance
directly in Python, outside of the manager.  Here is an example:

    p = Printer('Bob')      # Direct reference      (NO!)
    p.name = 'Bobby'        # Access to internals   (NO!)

Your first task is to modify the Actor class to prevent this by
raising a RuntimeError if an actor is ever created in this way.  If
you can't even create an actor, then clearly you can't look inside
or modify it!

The following test verifies the correct behavior.
"""

def test_instantiation():
    # This should fail
    try:
        p = Printer("Bob")
        assert False,"FAIL: Should not be here"
    except RuntimeError as err:
        print('Good Actor!')

test_instantiation()

"""Exercise 05  Enabling the Manager

The manager is a critical part of the actor system.  Not only does
it deliver messages to actors, it also provides a `spawn()` method
for creating new actors.  Your task is to modify the Manager class
so that it, and it alone, can create Actor instances (via spawn).
References to these instances are held internally, but are never
otherwise returned or exposed.

The following example should work without errors!

Note: This exercise presents a bit of puzzler in that the previous
exercise made it an error to directly create instances!  Somehow you are
going to have to reconcile THAT with this exercise.  Also,
there is the problem of the Actor `__init__()` method which
receives arguments and initializes the actor when it's created.
How is that method going to get called?
"""

def spawn_example():
    pass

#spawn_example()



























