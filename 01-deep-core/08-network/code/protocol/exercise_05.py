from exercise_01 import recreate_message
from protocol import ChatMessage, PlayerUpdate


def parse_line(data, index):
    if index < len(data):
        end = data.find(b"\n", index)
        if end < 0:
            return None
        return (data[index : end + 1], end + 1)


def parse_message(data, index):
    if not (m := parse_line(data, index)):
        return None
    msgtype, index = m
    if not (m := parse_line(data, index)):
        return None
    msgsize, index = m
    msgsize = int(msgsize)
    if index + msgsize > len(data):
        return None
    payload = data[index : index + msgsize]
    index += msgsize
    return (
        recreate_message(
            msgtype.decode("utf-8").strip(), payload.decode("utf-8").strip()
        ),
        index,
    )


class MessageReceiver:
    def __init__(self):
        self.data = b""  # Accumulated data

    def send(self, data):
        self.data += data
        messages = []
        index = 0

        while m := parse_message(self.data, index):
            msg, index = m
            messages.append(msg)
        self.data = self.data[index:]
        return messages


def test_new_receiver():
    print("Testing receiver")
    print("Launching helper program (testsmg.py)")
    import subprocess
    import sys
    import time
    from pathlib import Path

    script_path = Path(__file__).parent / "testmsg.py"
    p = subprocess.Popen([sys.executable, str(script_path)])

    try:
        # Wait for it to start up
        time.sleep(0.5)

        # Establish a socket connection
        import socket

        sock = socket.create_connection(("localhost", 19000))
        messages = []

        # --- YOU IMPLEMENT THIS PART
        receiver = MessageReceiver()

        # Concept: Read data in large chunks off of the sock and feed
        # into the receiver to reconstruct messages
        while chunk := sock.recv(1000):
            messages.extend(receiver.send(chunk))

        # Receive all data on `sock` and use `receiver` to add fully
        # formed messages to the `messages` list.
        # --- YOU IMPLEMENT ABOVE

        # Verify that the received messages are correct
        assert messages == [
            ChatMessage("Dave", "Hello World"),
            PlayerUpdate("Paula", 23, 41),
        ]

        sock.close()
        print("Good new receiver!")

    finally:
        p.terminate()


# Uncomment when ready
if __name__ == "__main__":
    test_new_receiver()
