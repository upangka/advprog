import random

from exercise_05 import MessageReceiver
from protocol import ChatMessage, PlayerUpdate, encode_message


def test_sans_io():
    print("Testing Sans I/O")
    messages = [
        ChatMessage("Dave", "Hello World"),
        PlayerUpdate("Paula", 23, 41),
    ]
    # --- YOU IMPLEMENT THIS PART
    receiver = MessageReceiver()
    # Fake the behavior of the `testmsg.py` program by feeding data fragments to
    # `receiver` to create messages. Messages should be added to `received_messages`
    # as before.

    # --- YOU IMPLEMENT ABOVE
    received_messages = []
    encode_data = b"".join(encode_message(msg) for msg in messages)
    n = 0
    while chunk := encode_data[n : n + random.randint(1, 10)]:
        received_messages.extend(receiver.send(chunk))
        n += len(chunk)
    # Verify that the answer worked
    assert received_messages == [
        ChatMessage("Dave", "Hello World"),
        PlayerUpdate("Paula", 23, 41),
    ]
    print("Good Sans I/O")


if __name__ == "__main__":
    test_sans_io()
