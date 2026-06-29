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

输出

```sh
Move to: (5, 10)
Move to: (2, 15)
Boosted to: 125
<__main__.Player object at 0x7f70fd3ba3c0> is going way
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


[exercise_03.py](./code/actors/exercise_03.py)

```python
from dataclasses import dataclass

from actors import Actor, Manager, Message

@dataclass
class Message:
    source: str
    dest: str


@dataclass
class Move(Message):
    """继承dataclass生成的__init__参数签名
    >>> import inspect
    >>> inspect.signature(Move)
    <Signature (source: str, dest: str, dx: int, dy: int) -> None>
    """
    dx: int
    dy: int


@dataclass
class Boost(Message):
    """
    <Signature (source: str, dest: str, amount: int) -> None>
    """
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
            print(f"Move to: ({self.x}, {self.y})")
        elif isinstance(msg, Boost):
            self.energy += msg.amount
            print(f"Boosted to: {self.energy}")
        else:
            # Unrecognized message
            pass


def example():
    m = Manager()
    m.spawn("bob", Player())
    m.send(Move("example", "bob", 5, 10))
    m.send(Move("example", "bob", -3, 5))
    m.send(Boost("example", "bob", 25))
    del m
```

输出

```python
Move to: (5, 10)
Move to: (2, 15)
Boosted to: 125
<__main__.Player object at 0x7fc2c7d7e510> is going way
```


# Actor

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


1. 用 `__new__` 阻止用户直接实例化 `Actor`，用户端根本接触不到Actor. 只能用address(Manager._actors.key)逻辑操作Actor
2. Manager提供`_get_actor`“逃生出口”从而可以合法的从 `Manager` 中获得 `Actor`,主要用于内部测试，从而强制实现 Actor 模型的封装规则。


## Exercise 04  Preventing Direct Instantiation

The only allowed way to refer to an Actor is by its address--a string.  One way to circumvent this would be to create an Actor instance directly in Python, outside of the manager.  Here is an example:

- circumvent /ˌsɜːr.kəmˈvent/ v. 绕过；规避；设法克服（指避开某个规则、限制或障碍，找到绕过它的方法。

```python
    p = Printer('Bob')      # Direct reference      (NO!)
    p.name = 'Bobby'        # Access to internals   (NO!)
```

Your first task is to modify the Actor class to prevent this by
raising a RuntimeError if an actor is ever created in this way.  If
you can't even create an actor, then clearly you can't look inside
or modify it!

The following test verifies the correct behavior.

```python
def test_instantiation():
    # This should fail
    try:
        p = Printer("Bob")
        assert False, "FAIL: Should not be here!!!"
    except RuntimeError as err:
        print("Good Actor")
```

[exercise_04.py](./code/actors/exercise_04.py)

```python
class Actor(ABC):

    def __new__(cls, *args, **kwargs):
        """阻止用户实例化"""
        raise RuntimeError("Can't create instance")

    def __del__(self):
        print(f"{self} is going away")

    @abstractmethod
    def handle_message(self, message: Message): ...


class Manager:
    def __init__(self):
        """
        address (str) -> actor(Actor)
        """
        self._actors = {}

    def send(self, msg: Message):
        if msg.dest in self._actors:
            self._actors[msg.dest].handle_message(msg)

    def _get_actor(self, address: str):
        """逃生出口，方便内部测试
        Create actor via Manager, but provide an escape hatch(逃生出口)
        for getting instance for testing,debugging
        """
        return self._actors[address]

    def spawn(self, address: str, actor_cls: type[Actor], *args):
        # Python底层机制，使用父类object的__new__
        actor = object.__new__(actor_cls, *args)
        actor.__init__(*args)
        self._actors[address] = actor
        return address
```