import time
from socket import *  # type: ignore

from protocol import ChatMessage, PlayerUpdate, encode_message


def main():
    sock = socket(AF_INET, SOCK_STREAM)
    sock.bind(("localhost", 19000))
    sock.listen()
    print("Connect to ('localhost',19000) to get messages")
    client, addr = sock.accept()

    msgs = [ChatMessage("Dave", "Hello World"), PlayerUpdate("Paula", 23, 41)]

    for msg in msgs:
        data = encode_message(msg)
        client.sendall(data)
        print(f"testsmg: sent {type(msg).__name__} ({len(data)} bytes)")
        time.sleep(0.1)  # 模拟网络延迟

    client.close()
    sock.close()


if __name__ == "__main__":
    main()
