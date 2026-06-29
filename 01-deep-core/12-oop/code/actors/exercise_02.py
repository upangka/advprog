from actors import Actor, Manager, Message
from exercise_01 import Printer


def manager_example():
    m = Manager()
    # Create a few actors
    m.spawn("GuangZhou", Printer())
    m.spawn("ShenZhen", Printer())

    # Send a few messages
    m.send(
        Message(source="GuangZhou", dest="ShenZhen", content="想你的风还是吹到了广州")
    )
    m.send(Message(source="ShenZhen", dest="GuangZhou", content="我在深圳也很想你"))

    # Delete the manager
    # This should produce two messages about Printer actor going away
    print("About to delete manager")
    del m
    print("Manager deleted")


if __name__ == "__main__":
    manager_example()
