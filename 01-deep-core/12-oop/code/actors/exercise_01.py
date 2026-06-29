from actors import Actor, Manager, Message


class Printer(Actor):
    def handle_message(self, msg: Message):
        print(f"{msg.dest}: {msg.source} said: {msg.content}")


def printer_example():
    import time

    m = Manager()
    m.spawn("printer", Printer())
    m.send(
        Message(
            source="example",
            dest="printer",
            content="Hello World. From ShenZhen, China",
        )
    )
    time.sleep(5)
    m.send(
        Message(source="example", dest="printer", content="Are you still there World?")
    )


if __name__ == "__main__":
    printer_example()
