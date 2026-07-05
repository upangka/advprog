from dataclasses import dataclass


@dataclass
class Point:
    three: int
    two: int
    one: int

    def total(self):
        return self.three * 3 + self.two * 2 + self.one

    def __lt__(self, other):
        return self.total() < other.total()

    def __eq__(self, other):
        return self.total() == other.total()

    def __gt__(self, other):
        return self.total() > other.total()

    @classmethod
    def from_input(cls):
        three = int(input())
        two = int(input())
        one = int(input())
        return cls(three=three, two=two, one=one)


apple_points = Point.from_input()
banana_points = Point.from_input()

if apple_points > banana_points:
    print("A")
elif banana_points > apple_points:
    print("B")
else:
    print("T")
