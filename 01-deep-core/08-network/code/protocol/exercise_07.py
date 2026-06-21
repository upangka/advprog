import random
import time

from exercise_06 import MessageReceiver
from protocol import ChatMessage, PlayerUpdate, encode_message


def perf_test_sans_io():

    messages = [ChatMessage("Dave", "Hello World"), PlayerUpdate("Paula", 23, 41)]

    receiver = MessageReceiver()
    raw_data = b"".join(encode_message(msg) for msg in messages) * 50000
    n = 0

    start = time.perf_counter()
    while chunk := raw_data[n : n + random.randint(1, 100)]:
        receiver.send(chunk)
        n += len(chunk)
    end = time.perf_counter()

    print(f"{100000 / (end -start):.0f} messages per second")


if __name__ == "__main__":
    perf_test_sans_io()
