class Stack:
    def __init__(self):
        self._items: tuple = ()
        self._size = 0

    def push(self, item):
        self._items = (item, self._items)
        self._size += 1

    def pop(self):
        if self._size <= 0:
            raise LookupError(f"pop from empty {type(self).__name__}")
        item, self._items = self._items
        self._size -= 1
        return item

    def __len__(self):
        return self._size


def test_link_tuple():
    s = Stack()
    s.push(1)
    s.push(2)
    s.push(3)

    r = [s.pop() for _ in range(3)]
    assert r == [3, 2, 1]

    try:
        s.pop()
        print("Error!!! Why I'm here")
    except Exception as e:
        assert type(e) == LookupError
    print("Good test")


if __name__ == "__main__":
    test_link_tuple()
