Introduction:

Arjoon is implementing a distributed messaging system for a game.
The game has a large collection of different game messages that are represented by different classes. When messages are sent over the network, they must be serialized into bytes and deserialized on the receiver. To do this, he has decided that each message will be encoded according to the following protocol:

1. The message type will be encoded as a UTF-8 string, terminated by a newline (`\r\n`).

2. The message size (an integer) follows and will be encoded as a UTF-8 string terminated by a newline (`\r\n`).

3. This is followed by the message contents, encoded as a UTF-8 JSON string.

To illustrate, suppose that the following classes represent a few different kinds of messages:

[protocol.py](./code/protocol/protocol.py)

```python
import json

class Message:
    _registry = {}

    def __init_subclass__(cls) -> None:
        print(f"Initing {cls.__name__}")
        Message._registry[cls.__name__] = cls

        # @classmethod
        # def from_untrust(cls, **kwargs):
        #     for key, msgcls in cls.__init__.__annotations__.items():
        #         if not isinstance(kwargs[key], msgcls):
        #             raise TypeError(
        #                 f"{kwargs[key]} is {type(kwargs[key]).__name__} expect {cls.__name__}"
        #             )
        #     return cls(**kwargs)

        # 下面的方式会好一些
        annotations = cls.__init__.__annotations__

        # monkey pathging
        @classmethod
        def from_untrust(cls, **kwargs):
            for key, value in kwargs.items():
                if not isinstance(value, annotations[key]):
                    raise TypeError(
                        f"{kwargs[key]} is {type(kwargs[key]).__name__} expect {cls.__name__}"
                    )
            return cls(**kwargs)

        cls.from_untrust = from_untrust

    def __eq__(self, other) -> bool:
        return type(self) == type(other) and vars(self) == vars(other)

    def __repr__(self) -> str:
        return f"{type(self).__name__}<{repr(vars(self))}>"

class ChatMessage(Message):
    def __init__(self, playerid: str, text: str):
        self.playerid = playerid
        self.text = text

class PlayerUpdate(Message):
    def __init__(self, playerid: str, x: int, y: int):
        self.playerid = playerid
        self.x = x
        self.y = y
```

Here's a function that encodes a message according to the above protocol.

```python
def encode_message(msg: Message) -> bytes:
    msgtype = type(msg).__name__.encode('utf-8') + b'\r\n'
    payload = json.dumps(msg.__dict__).encode('utf-8')
    size = str(len(payload)).encode('utf-8') + b'\r\n'
    return msgtype + size + payload
```


An example that shows the encoding for a few messages

```python
def example():
    msg1 = ChatMessage("鲨鱼のJavthon", "Hello World")
    msg2 = PlayerUpdate("Pkmer", 23, 56)
    print(encode_message(msg1))
    print(encode_message(msg2))

example()
```

# Exercise 1 - The recreator

Taking a message and turning it into bytes is straightforward. However, how do you actually get a message back from bytes on the receiver?

Your first task is to write a message creation function that takes the name of message type as a string and the JSON-encoded payload (as text) and turns it back into a proper Python object. The function should raise an exception if the specified message type doesn't correspond a valid message definition.

Recreating a message from data received over the network involves a potentially hostile actor. How do you do it in a way that doesn't introduce weird security holes? How do you test it?

Note: You can use `json.loads()` to convert JSON data into a Python dict.

[exercise_01.py](./code/protocol/exercise_01.py)

```python
def recreate_message(msgtype: str, payload: str) -> Message:

    msgcls = Message._registry[msgtype]
    # if msgtype == "ChatMessage":
    #     msgcls = ChatMessage
    # elif msgtype == "PlayerUpdate":
    #     msgcls = PlayerUpdate
    # else:
    #     raise RuntimeError(f"Not support {msgtype}")

    # 防御性编程
    if len(payload) > 1000:
        raise RuntimeError("Message payload exceeds maximum allowed size (1000 bytes)")
    kwargs = json.loads(payload)
    # for key,cls in msgcls.__init__.__annotations__.items():
    #     if not isinstance(kwargs[key],cls):
    #         raise TypeError(f'{kwargs[key]} is {type(kwargs[key]).__name__} expect {cls.__name__}')

    return msgcls.from_untrust(**kwargs)

def test_recreator():
    msg1 = recreate_message('ChatMessage', '{"playerid": "Dave", "text": "Hello World"}')
    assert msg1 == ChatMessage('Dave', 'Hello World')

    msg2 = recreate_message('PlayerUpdate', '{"playerid": "Paula", "x": 23, "y": 41}')
    assert msg2 == PlayerUpdate('Paula', 23, 41)
    print("Ok creator.")

    # A message of invalid type
    try:
        msg3 = recreate_message('HackerMsg', '{"x": 666}')
        assert False, "Why did this work?!?! Bad creator!"
    except Exception as e:
        print("Good creator!")

    # Message with incomplete arguments
    try:
        msg4 = recreate_message('PlayerUpdate', '{"playerid": "Paula"}')
    except Exception as e:
        # Above message is missing fields for x/y. Could this be caught?
        print("Very good creator!")

    # Message with wrong argument types
    try:
        msg5 = recreate_message('PlayerUpdate', '{"playerid": "Paula", "x": "two", "y": 123.45}')
    except Exception as e:
        # The x and y values violate Python type-hints. Could this be caught?
        print("Excellent creator!")
```


# Exercise 2 - The Receiver

To receive a message on a network connection, you've got to write
code that receives fragments of bytes and reassembles them back into
message objects.

- reassembles /ˌriː.əˈsem.bəlz/ v. 重新组装；重新拼合（指将拆散或分散的碎片、部分重新组合成一个完整的整体。

A common object used for network communication is a "socket". A
socket has a method `recv(maxsize)` that receives bytes (up to a
requested maximum size). It returns any data that is available
(which might be less than the given maximum). <span style="color: #08a6db ">An empty byte-string
is returned when a connection is closed (meaning no more data will
arrive)</span>.

The issue with sockets is that they present data as an endless
stream, but are somewhat unpredictable in behavior. For example,
there's no guarantee that the `maxsize` number of bytes will be
returned. So, if you say `sock.recv(100)`, you might get 100 bytes
of data, but you might get less than that. If you were expecting 100
bytes, then you might have to call `sock.recv()` again to get more
data. A second problem is that there is no `unreceive`. You can't
shove already received data back into a socket. So, if you receive
too much data, you'll need to keep the extra data around somewhere.
Alternatively, you can try to write your code in a way where you
carefully work to not "over-receive."

- shove /ʃʌv/ v. 推；塞；硬塞（指用力把某物推入或塞进某个空间，带有一种"强行"的意味

With this in mind, your task is to write functionality that reads
bytes off of a socket and produces a fully formed Message instance
using the `recreate_message()` function you just wrote. If the
function is called repeatedly on the same socket, it should return a
new message each time unless there is no more data or some other
problem occurs.

A few helper functions have been written to try and make it easier.
However, there are many "issues" with this code as we'll see.


[exercise_02.py](./code/protocol/exercise_02.py)

```python
def receive_line(sock) -> bytes:
    # Receive a single line of data. Or return b'' if the connection
    # is closed before a complete line is read.
    line = b''
    while (c := sock.recv(1)):
        line += c
        if c == b'\n':
            break
    else:
        return b''
    return line

def receive_exactly(sock, nbytes: int) -> bytes:
    # Receive an exact number of bytes. Or return b'' if the
    # connection is closed prematurely.
    data = b''
    while (chunk := sock.recv(nbytes)):
        data += chunk
        nbytes -= len(chunk)
    return data if nbytes == 0 else b''

def receive_message(sock) -> Message | None:
    # Receive a message on a socket or return None if no message is found.
    # --- YOU IMPLEMENT
    # Use receive_line() and receive_exactly() to read a message.

    if not (msgtype := read_line(sock)):
        return None
    if not (size := read_line(sock)):
        return None
    if not (payload := receive_exactly(sock, int(size))):
        return None
    # Create the result message
    return recreate_message(
        msgtype.decode("utf-8").strip(), payload.decode("utf-8").strip()
    )
```

To test the above function with an actual socket, Arjoon has written
a separate program [testmsg.py](./code/protocol/testmsg.py) which you can find in this same
directory. This program must be running in a separate Python
process. Thus, Arjoon has launched it as subprocess. Once running,
the test connects to it to receive sample network messages back.

```python
def test_receiver():
    print("Testing receiver")
    print("Launching helper program(testmsg.py)")
    import subprocess
    import sys
    import time
    from pathlib import Path

    try:
        script_path = Path(__file__).parent / "testmsg.py"
        p = subprocess.Popen([sys.executable, str(script_path)])
        # wait for it to start up
        time.sleep(1)

        # Establish a socket connection
        import socket

        sock = socket.create_connection(("localhost", 19000))
        messages = []
        while msg := receive_message(sock):
            messages.append(msg)
        assert messages == [
            ChatMessage("Dave", "Hello World"),
            PlayerUpdate("Paula", 23, 41),
        ]
        sock.close()
        print("Good receiver!")
    finally:
        p.terminate()
```

```python
# Uncomment when ready
# test_receiver()
```


# Exercise 3 - Is it testable?

Think about the testing strategy in Exercise 2 for a moment. It involves a test, but that test involves sockets. In fact, a separate program has to be running to complete the test! One might ask the question if the code is actually testable in any kind of easy way. Maybe not.

Your challenge. Can you devise some better way to test this code than launching a separate program and using an actual socket? Perhaps it's possible to create a "fake" or "mock" socket object to use in testing.

> Discussion:
> 1. an actual socket is a fairly complicated object with more than 40 methods defined on it. Do I need to fake all of that?
> 2. Earlier code (i.e., receive_line) has been coded directly to the socket API. Is there any kind of type-checking being applied to that? If so, hwo does our FakeSocket fit into all of that?  
 


```python
class FakeReceiver:
    def __init__(self, data):
        self.data = data
        self.n = 0

    def recv(self, maxsize: int):
        chunk = self.data[self.n : self.n + maxsize]
        self.n += len(chunk)
        return chunk


def test_receive_message():
    # YOU IMPLEMENT.
    #
    # Can you write a test for receive_message() that doesn't involve an actual socket connection?
    # Note: You may need to write some additional support code.

    # Concept: A "round-trip" test(往返测试). Messages get encoded into raw
    # data. Read from a fake socket. Received messages should be same.

    messages = [ChatMessage("Dave", "Hello World"), PlayerUpdate("Paula", 23, 41)]

    raw_data = b"".join([encode_message(m) for m in messages])
    sock = FakeReceiver(raw_data)
    received_messages = []
    while msg := receive_message(sock):
        received_messages.append(msg)

    assert received_messages == messages
```

```python
# Uncomment:
# test_receive_message()
```


# Exercise 4 - Performance

It has been determined that the messaging system must be minimally able to receive and decode 100000 messages per second.

How would you write a test to ensure this and does the `receive_message()` function satisfy the requirement?

```python
def perf_test():
    # YOU IMPLEMENT
    ...

# Uncomment:
# perf_test()
```



# Exercise 5 - Inversion of I/O

Sometimes when faced with a problem, it is useful to "invert" the problem.
What is the central problem with receiving messages? In the above code,
it might be the waiting for data to arrive. You have to receive from a
socket, make sure you don't receive *too much* data, and then put everything
back together. How might you write the code if you *already* had all
of the data?

Peter has proposed the following `MessageReceiver` class

```python
class MessageReceiver:
    def __init__(self):
        self.data = b''    # Accumulated data

    def send(self, data):
        self.data += data
        messages = []
        index = 0
        while (m := parse_message(self.data, index)):
            msg, index = m
            messages.append(msg)
        self.data = self.data[index:]
        return messages
```

Instead of waiting for data, this class works by having you "send" data into
it. The `send()` method saves the data and then returns a list of all
message objects that it can find. The central work is performed by a
parse_message() function that is completely independent of any I/O. It
only works on raw data. Here is the parsing code. These functions
are similar to the parsing in Project 6 (config).

```python
def parse_line(data, index):
    # Parse a line of data or return None if no match.
    if index < len(data):
        end = data.find(b'\n', index)
        if end < 0:
            return None
        return (data[index:end+1], end+1)

def parse_message(data, index):
    # Parse a complete message or return None if no match.
    if not (m := parse_line(data, index)):
        return None
    msgtype, index = m
    if not (m := parse_line(data, index)):
        return None
    msgsize, index = m
    msgsize = int(msgsize)
    if msgsize > (len(data) - index):
        return None
    payload = data[index:index+msgsize]
    index += msgsize
    return (recreate_message(msgtype.strip().decode('utf-8'),
                             payload.decode('utf-8')), index)
```

YOUR TASK: Your first task is to show how you would use the
`MessageReceiver()` class with an actual socket by recreating
the earlier test code. You need to fill in part of this code
as indicated.

```python
def test_new_receiver():
    print("Testing receiver")
    print("Launching helper program (testsmg.py)")
    import sys, subprocess, time
    p = subprocess.Popen([sys.executable, "testsmg.py"])

    try:
        # Wait for it to start up
        time.sleep(0.5)

        # Establish a socket connection
        import socket
        sock = socket.create_connection(('localhost', 19000))
        messages = []

        # --- YOU IMPLEMENT THIS PART
        receiver = MessageReceiver()
        ...

        # Receive all data on `sock` and use `receiver` to add fully
        # formed messages to the `messages` list.
        # --- YOU IMPLEMENT ABOVE

        # Verify that the received messages are correct
        assert messages == [
            ChatMessage('Dave', 'Hello World'),
            PlayerUpdate('Paula', 23, 41)
        ]

        sock.close()
        print('Good new receiver!')

    finally:
        p.terminate()

# Uncomment when ready
# test_new_receiver()
```


# Exercise 6 - Sans I/O

Can you devise a test similar to Exercise 5 that doesn't involve any sockets
at all? Can you expand the test to cover different different corner cases
related to data sizes and fragmentation? (i.e., faking the unpredictable nature
of sockets).

```python
def test_sans_io():
    print('Testing Sans I/O')
    messages = []

    # --- YOU IMPLEMENT THIS PART
    receiver = MessageReceiver()
    ...
    # Fake the behavior of the `testmsg.py` program by feeding data fragments to
    # `receiver` to create messages. Messages should be added to `messages`
    # as before.

    # --- YOU IMPLEMENT ABOVE

    # Verify that the answer worked
    assert messages == [
        ChatMessage('Dave', 'Hello World'),
        PlayerUpdate('Paula', 23, 41)
    ]
    print('Good Sans I/O')

# Uncomment when ready
# test_sans_io()
```



# Exercise 7 - Performance (redux)

Recreate your performance test from Exercise 4 here using the new
`MessageReceiver` class. Is it faster or slower?

```python
def perf_test_sans_io():
    # You implement
    ...

# Uncomment
# perf_test_sans_io()
```