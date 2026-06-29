# Introduction

Arjoon is working on a distributed system involving message passing.
The system is to be built around something known as the "actor
model." In the actor model, a system is composed of independent
objects called "actors".

Actors coordinate by sending messages to each other. Each actor has
an associated address and this address is embedded in each message.
There is no other mechanism for communication nor is there any
shared state. Think of each actor as a completely independent
entity that is isolated from all other actors except for the ability
to receive a message.

In response to a message, an actor can perform local processing,
send messages to other actors that it knows about, or create new
actors. It can also ignore the message if it doesn't understand it.

To implement the actor model, Arjoon has started to write the
following code. It consists of a `Message` class that is used to
encode messages. The `Actor` class is an abstract class that
specifies the required interface for `Actor` instances--actors must
be defined by inheriting from this class. Finally, there is a
`Manager` class that has runtime functionality related to sending
messages and creating (spawning) new actors.

Most of this project is going to involve thinking about these
classes, their overall design, and their interaction with each other.

| 组件 | 角色 |
|---|---|
| Actor | 独立实体，通过消息通信，无共享状态 |
| Message | 消息载体，包含目标 Actor 地址 |
| Manager | 运行时管理器，负责发送消息和创建 Actor |

[actors.py](./code/actors/actors.py)

```python
import abc
from dataclasses import dataclass

@dataclass
class Message:
    source: str
    dest: str
    content: str


class Actor(abc.ABC):
    """Actors are objects that receive and respond to messages"""

    def __del__(self):
        print(f"{self} is going way")

    @abc.abstractmethod
    def handle_message(self): ...


class Manager:
    def __init__(self):
        # address: str -> actor: Actor:
        self._actors = {}

    def send(self, msg: Message):
        if msg.dest in self._actors:
            self._actors[msg.dest].handle_message(msg)

    def spawn(self, address: str, actor: Actor):
        self._actors[address] = actor
        return address
```

# Exercise 01 Hello World

Your first task is to try an example involving the above code.  Here
is an implementation of an actor that receives messages and simply
prints them out.

[exercise_01.py](./code/actors/exercise_01.py)

```python
from actors import Actor,Message,Manager

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
```

# Exercise 02 Understanding the Manager

The purpose of the `Manager` class is to create a managed environment for the `Actor` instances and to handle all of the associated messaging. Actors are always associated with an enclosing Manager. When the Manager goes away, the Actors contained within it should also go away. Verify that this seems to happen by
trying this example:

验证 Manager 与 Actor 之间的生命周期绑定关系。
当 Manager 对象被销毁（或不再被引用）时，它所管理的所有 Actor 是否也一同被销毁？Yes

[exercise_02.py](./code/actors/exercise_02.py)

```python
from actors import Actor, Manager, Message
from exercise_01 import Printer


def manager_example():
    m = Manager()
    # Create a few actors
    m.spawn("GuangZhou", Printer())
    m.spawn("ShenZhen", Printer())

    # Send a few messages
    m.send(
        Message(source="GuangZhou", dest="ShenZhen", content="想你的风还是吹到了广州")
    )
    m.send(Message(source="ShenZhen", dest="GuangZhou", content="我在深圳也很想你"))

    # Delete the manager
    # This should produce two messages about Printer actor going away
    print("About to delete manager")
    del m
    print("Manager deleted")
```

```sh
From GuangZhou to ShenZhen said: 想你的风还是吹到了广州
From ShenZhen to GuangZhou said: 我在深圳也很想你
About to delete manager
<exercise_01.Printer object at 0x7f86116d6cf0> is going way
<exercise_01.Printer object at 0x7f861195fc50> is going way
Manager deleted
```
