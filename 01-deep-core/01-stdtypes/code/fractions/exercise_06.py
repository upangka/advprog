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


# We will define a proper class
class Fraction:
    def __init__(self, numerator, denominator):
        d = gcd(numerator, denominator)
        self.numerator = numerator // d
        self.denominator = denominator // d

    # Define various magic methods for Python operators
    def __add__(self, other: Fraction) -> Fraction:
        return Fraction(
            self.numerator * other.denominator + self.denominator * other.numerator,
            self.denominator * other.denominator,
        )

    def __sub__(self, other: Fraction) -> Fraction:
        return Fraction(
            self.numerator * other.denominator - self.denominator * other.numerator,
            self.denominator * other.denominator,
        )

    def __mul__(self, other: Fraction) -> Fraction:
        return Fraction(
            self.numerator * other.numerator,
            self.denominator * other.denominator,
        )

    def __truediv__(self, other: Fraction) -> Fraction:
        return Fraction(
            self.numerator * other.denominator, self.denominator * other.numerator
        )

    def __repr__(self) -> str:
        return f"Fraction(numerator={self.numerator},denominator={self.denominator})"


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


if __name__ == "__main__":
    test_frac()
