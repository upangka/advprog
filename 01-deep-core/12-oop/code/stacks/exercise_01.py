class StackEmptyError(Exception):
    pass


class Stack:
    def __init__(self, *, container=None):
        self._items = container if container else []

    def pop(self):

        if not self._items:
            raise StackEmptyError("pop from empty stack")

        return self._items.pop()

    def push(self, item):
        self._items.append(item)

    def __len__(self):
        return len(self._items)


def test_stack(s):
    s.push(23)
    s.push(45)
    assert len(s) == 2
    assert s.pop() == 45
    assert s.pop() == 23
    assert len(s) == 0
    print("Good stack!")


if __name__ == "__main__":
    test_stack(Stack())
    from array import array

    test_stack(Stack(container=array("i")))
