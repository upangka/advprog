from actors import Actor, Manager, Message


class Player(Actor):

    def __init__(self):
        self.x = 0
        self.y = 0
        self.energy = 100

    def handle_message(self, msg: Message):
        parts = msg.content.split()

        if parts[0] == "move":
            self.x += int(parts[1])
            self.y += int(parts[2])
            print(f"Move to: ({self.x}, {self.y})")
        elif parts[0] == "boost":
            self.energy += int(parts[1])
            print(f"Boosted to: {self.energy}")
        else:
            # Unrecognized message
            pass


def old_example():
    m = Manager()
    m.spawn("bob", Player())

    # Need more structure on this
    m.send(Message("example", "bob", "move 5 10"))
    m.send(Message("example", "bob", "move -3 5"))
    m.send(Message("example", "bob", "boost 25"))
    del m


if __name__ == "__main__":
    old_example()
