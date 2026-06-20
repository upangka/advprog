from exercise_01 import recreate_message
from protocol import ChatMessage, Message, PlayerUpdate


def read_line(sock) -> bytes:
    """
    Receive a single line of data. Or return b'' if the connection
    is closed before a comlete line is read.
    """
    line = b""
    while c := sock.recv(1):
        line += c
        if c == b"\n":
            break
    else:
        return b""
    return line


def receive_exactly(sock, nbytes: int) -> bytes:
    """
    Receive an exact number of bytes. Or return b'' if the
    connection is closed prematurely
    """
    data = b""
    # sock.recv(0) >>> b''
    while chunk := sock.recv(nbytes):
        data += chunk
        nbytes -= len(chunk)
    return data if nbytes == 0 else b""


def receive_message(sock) -> Message | None:
    # Receive a message on a socket or return None if no message is found.
    # --- YOU IMPLEMENT
    # Use receive_line() and receive_exactly() to read a message.
    
    if not (msgtype := read_line(sock)):
        return None
    if not (size := read_line(sock)):
        return None
    if not (payload := receive_exactly(sock,int(size))):
        return None
    # Create the result message
    return recreate_message(
        msgtype.decode("utf-8").strip(), payload.decode("utf-8").strip()
    )


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

if __name__ == '__main__':
    test_receiver()