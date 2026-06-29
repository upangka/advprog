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