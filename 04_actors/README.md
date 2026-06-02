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

---

Arjoon 正在开发一个涉及消息传递的分布式系统。
这个系统将围绕一种称为“Actor 模型”的东西来构建。在 Actor 模型中，一个系统由称为“Actor”的独立对象组成。

Actor 之间通过相互发送消息来协调。每个 Actor 都有一个关联的地址，这个地址被嵌入到每条消息中。
没有其他的通信机制，也没有任何共享状态。将每个 Actor 视为一个完全独立的实体，除了接收消息的能力外，它与所有其他 Actor 隔离。

在响应一条消息时，Actor 可以执行本地处理、向它所知道的其他 Actor 发送消息，或者创建新的 Actor。
如果它不理解这条消息，也可以忽略它。

为了实现 Actor 模型，Arjoon 已经开始编写以下代码。
它包含一个用于编码消息的 `Message` 类。
`Actor` 类是一个抽象类，它指定了 `Actor` 实例所需的接口——Actor 必须通过继承这个类来定义。
最后，有一个 `Manager` 类，它包含与发送消息和创建（生成）新 Actor 相关的运行时功能。

这个项目的大部分内容将涉及思考这些类、它们的整体设计以及它们之间的交互。
