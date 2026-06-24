from __future__ import annotations


def gcd(a, b):
    # Greatest common divisor
    while b:
        a, b = b, a % b
    return a


def make_frac(numerator, denominator):
    return Fraction(numerator, denominator)


def numerator(f):
    return f.numerator


def denominator(f):
    return f.denominator


from typing import Union, overload


# We will define a proper class
class Fraction:
    def __init__(self, numerator, denominator):
        d = gcd(numerator, denominator)
        self.numerator = numerator // d
        self.denominator = denominator // d

    # Define various magic methods for Python operators

    @overload
    def __add__(self, other: Fraction) -> Fraction: ...
    @overload
    def __add__(self, other: int) -> Fraction: ...
    def __add__(self, other):
        return Fraction(
            self.numerator * other.denominator + self.denominator * other.numerator,
            self.denominator * other.denominator,
        )

    __radd__ = __add__

    # 相比使用overload使用Union更加方便
    def __sub__(self, other: Union[Fraction, int]) -> Fraction:
        return Fraction(
            self.numerator * other.denominator - self.denominator * other.numerator,
            self.denominator * other.denominator,
        )

    def __rsub__(self, other: Union[Fraction, int]):
        return Fraction(
            other.numerator * self.denominator - other.denominator * self.numerator,
            other.denominator * self.denominator,
        )

    def __mul__(self, other: Union[Fraction, int]) -> Fraction:
        return Fraction(
            self.numerator * other.numerator,
            self.denominator * other.denominator,
        )

    __rmul__ = __mul__

    def __truediv__(self, other: Union[Fraction, int]) -> Fraction:
        return Fraction(
            self.numerator * other.denominator, self.denominator * other.numerator
        )

    def __rtruediv__(self, other):
        return Fraction(
            self.denominator * other.numerator, self.numerator * other.denominator
        )

    def __repr__(self) -> str:
        return f"Fraction(numerator={self.numerator},denominator={self.denominator})"

    def __eq__(self, other: Fraction | int) -> bool:
        return self.numerator * other.denominator == self.denominator * other.numerator

    def __lt__(self, other: Fraction | int) -> bool:
        return self.numerator * other.denominator < self.denominator * other.numerator

    def __le__(self, other: Fraction | int) -> bool:
        return self.numerator * other.denominator <= self.denominator * other.numerator


def add_frac(a, b):
    return a + b


def sub_frac(a, b):
    return a - b


def mul_frac(a, b):
    return a * b


def div_frac(a, b):
    return a / b


def test_frac():
    a = make_frac(4, 6)
    assert (numerator(a), denominator(a)) == (2, 3)

    b = make_frac(-3, -4)
    assert (numerator(b), denominator(b)) == (3, 4)

    c = make_frac(3, -4)
    assert (numerator(c), denominator(c)) == (-3, 4)

    d = add_frac(a, b)
    assert (numerator(d), denominator(d)) == (17, 12)

    e = sub_frac(a, b)
    assert (numerator(e), denominator(e)) == (-1, 12)

    f = mul_frac(a, b)
    assert (numerator(f), denominator(f)) == (1, 2)

    g = div_frac(a, b)
    assert (numerator(g), denominator(g)) == (8, 9)

    print("Good fractions In old version")


def test_math():
    a = Fraction(4, 6)
    assert (a.numerator, a.denominator) == (2, 3)

    b = Fraction(-3, -4)
    assert (b.numerator, b.denominator) == (3, 4)

    # Requires the __add__() method
    c = a + b
    assert (c.numerator, c.denominator) == (17, 12)

    # Requires the __sub__() method
    d = a - b
    assert (d.numerator, d.denominator) == (-1, 12)

    # Requires the __mul__() method
    e = a * b
    assert (e.numerator, e.denominator) == (1, 2)

    # Requires the __truediv__() method
    f = a / b
    assert (f.numerator, f.denominator) == (8, 9)

    # Mixed type operations. Note: Python integers
    g = a + 1
    assert (g.numerator, g.denominator) == (5, 3)

    # Requires the __radd__() method
    g = 1 + a
    assert (g.numerator, g.denominator) == (5, 3)

    # Requires the __rsub__() method
    g = 1 - a
    assert (g.numerator, g.denominator) == (1, 3)

    h = a * 10
    assert (h.numerator, h.denominator) == (20, 3)

    # Requires the __rmul__() method
    h = 10 * a
    assert (h.numerator, h.denominator) == (20, 3)

    # Comparisons. For these, you'll need to implement
    # methods such as __eq__(), __ne__(), __lt__(), __le__(),
    # __gt__(), and __ge__().

    # To compare fractions you can perform comparisons like this:

    a = Fraction(2, 3)
    b = Fraction(4, 5)

    # a < b ==> a.numerator * b.denominator < a.denominator * b.numerator

    
    assert a != b  # 只实现了__eq__ 就支持 == 和 !=
    assert a == Fraction(2, 3)
    assert a < b   # 只实现了__lt__和__le__ 支持 <,<=,>,>=
    assert a <= b
    assert b > a
    assert b >= a
    print("Good fractions In new version")


if __name__ == "__main__":
    test_frac()
    test_math()
