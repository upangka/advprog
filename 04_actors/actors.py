
from dataclasses import dataclass

@dataclass
class Message:
    src: str
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


    def send(self,msg: Message):
        if msg.dest in self._actors:
            self._actors[msg.dest]


    def spawn(self,address: str,actor: Actor):
        self._actors[address] = actor
        return address


"""Exercise 01 Hello World

Your first task is to try an example involving the above code.  Here
is an implementation of an actor that receives messages and simply
prints them out.
"""


