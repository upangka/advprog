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

# Message

A key part of the actor system is the concept of a message.  A
message minimally contains information about the sender and
recipient.  This is encoded in the `source` and `dest` fields.
However, a message also contains some kind of content that is to be
interpreted by the receiver.

A debate has erupted about the interpretation of the content.  As
currently written, the content is encoded as a string.  Any
interpretation is based on string processing.  The following code
shows an example:

> **核心问题是**:
> 
> 如何让消息内容更加结构化？

| 方式 | 示例 | 问题 |
|---|---|---|
| 字符串 | `"move 5 10"` | 需要手动 `split()`，参数类型转换（`int`），容易出错 |
| 更结构化的方式 | `{"action": "move", "x": 5, "y": 10}` 或 `MoveCommand(5, 10)` | 更清晰、类型安全、可扩展 |


[messages.py](./code/actors/messages.py)

```python
from actors import Actor, Manager, Message

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
            print(f"Move to: ({self.x}, {self.y})")
        elif parts[0] == "boost":
            self.energy += int(parts[1])
            print(f"Boosted to: {self.energy}")
        else:
            # Unrecognized message
            pass


def old_example():
    m = Manager()
    m.spawn("bob", Player())

    # Need more structure on this
    m.send(Message("example", "bob", "move 5 10"))
    m.send(Message("example", "bob", "move -3 5"))
    m.send(Message("example", "bob", "boost 25"))
    del m
```


## Exercise 03

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

```python
    @dataclass
    class Move:
        dx : int
        dy : int

    @dataclass
    class Boost:
        amount : int
```

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