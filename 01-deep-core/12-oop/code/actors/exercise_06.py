from exercise_01 import Message
from exercise_04 import Manager, Printer


def test_example():
    """
    One approach: create via manager, but expose via
    special method, here is `_get_actor`
    """
    m = Manager()
    address = m.spawn("alice", Printer, "Alice")
    actor = m._get_actor(address)
    actor.handle_message(Message("test-example", "alice", "Sales and Press Contact"))
    assert actor.count == 1
    print("Good Test")


if __name__ == "__main__":
    test_example()
