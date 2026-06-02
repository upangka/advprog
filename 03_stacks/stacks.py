class Stack:
    def __init__(self):
        self.items = []

    def push(self,item):
        self.items.append(item)

    def pop(self):
        return self.items.pop()
    
    def __len__(self):
        return len(self.items)


def test_stack():
    s = Stack()
    s.push(3)
    s.push(6)

    assert len(s) == 2
    assert s.pop() == 6
    assert s.pop() == 3
    assert len(s) == 0
    print("Good Luck")

test_stack()
    
