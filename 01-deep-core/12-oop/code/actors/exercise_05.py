from exercise_04 import Actor, Manager, Message, Printer


def spawn_example():
    try:
        p = Printer("Bob")
        assert False, "FAIL: Should not be here"
    except RuntimeError as err:
        # Good
        pass

    m = Manager()
    try:
        m.spawn("alice", Printer("Alice"))
        assert False, "FAIL,Why am I here???"
    except RuntimeError as err:
        # Good
        pass

    address = m.spawn("alice", Printer, "Alice")
    assert isinstance(address, str), "spawn should return an address string"
    m.send(Message("spawn-example", address, "你是药也是毒,爱你是一场赌注"))


if __name__ == "__main__":
    spawn_example()
