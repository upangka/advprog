import random

from exercise_02 import receive_message
from protocol import ChatMessage, PlayerUpdate, encode_message


class FakeReceiver:
    def __init__(self, data):
        self.data = data
        self.n = 0

    def recv(self, maxsize: int):
        # Could introduce randomness to emulate an actual socket
        if maxsize > 1:
            maxsize = random.randint(1, maxsize)
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

    print("Good test_receive_message")


if __name__ == "__main__":
    test_receive_message()
