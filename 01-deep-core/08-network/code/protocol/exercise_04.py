import time

from exercise_02 import receive_message
from exercise_03 import FakeReceiver
from protocol import ChatMessage, PlayerUpdate, encode_message


def per_test():
    messages = [ChatMessage("Dave", "Hello World"), PlayerUpdate("Paula", 23, 41)]
    raw_data = b"".join([encode_message(m) for m in messages]) * 50000
    sock = FakeReceiver(raw_data)
    start = time.perf_counter()
    while msg := receive_message(sock):
        pass
    end = time.perf_counter()
    print(f"{100000 / (end - start):.0f} messages per second")


if __name__ == "__main__":
    per_test()
