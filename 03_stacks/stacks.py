class Stack:
    def __init__(self):
        self._items = []

    def push(self,item):
        self._items.append(item)

    def pop(self):
        if not self._items:
            raise StackEmptyError("Pop from an empty stack")
        return self._items.pop()
    
    def __len__(self):
        return len(self._items)


class StackEmptyError(Exception): pass

def test_stack(s):
    s.push(3)
    s.push(6)

    assert len(s) == 2
    assert s.pop() == 6
    assert s.pop() == 3
    assert len(s) == 0
    print("Good Luck")

test_stack(Stack())
    
