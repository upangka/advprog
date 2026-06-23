from typing import NamedTuple

# class Fraction(NamedTuple):
#     numerator: int
#     denominator: int

#     # You'll need to make modifications to pass the test below. Logically,
#     # you'll want to make it so the numerator/denominator are reduced to
#     # lowest terms as you might have done in an __init__() method. Sadly,
#     # doing that does NOT work (can you figure out why?)
#     # DOES NOT WORK! Can you think of an alternative that achieves the same
#     # effect?
#     def __new__(cls, numerator, denominator):
#         d = gcd(numerator, denominator)
#         numerator = numerator // d
#         denominator = denominator // d
#         # AttributeError: Cannot overwrite NamedTuple attribute __new__
#         return super().__new__(cls, numerator, denominator)

#     def __init__(self, numerator, denominator):
#         d = gcd(numerator, denominator)
#         self.numerator = numerator // d  # Would not work ever
#         self.denominator = denominator // d  # Would not work ever


class _Fraction(NamedTuple):
    numerator: int
    denominator: int


class Fraction(_Fraction):
    def __new__(cls, numerator, denominator):
        d = gcd(numerator, denominator)
        numerator = numerator // d
        denominator = denominator // d
        return super().__new__(cls, numerator, denominator)


def gcd(a, b):
    # Greatest common divisor
    # a, b = abs(a), abs(b) 需要符号
    while b:
        a, b = b, a % b
    return a


def make_frac(numer, denom):
    # d = gcd(numer, denom)
    return Fraction(numer, denom)


def numerator(f):
    return f.numerator


def denominator(f):
    return f.denominator


# This is the same as before. NO CHANGES MADE.
def add_frac(a, b):
    return make_frac(
        numerator(a) * denominator(b) + denominator(a) * numerator(b),
        denominator(a) * denominator(b),
    )


def sub_frac(a, b):
    return make_frac(
        numerator(a) * denominator(b) - denominator(a) * numerator(b),
        denominator(a) * denominator(b),
    )


def mul_frac(a, b):
    return make_frac(numerator(a) * numerator(b), denominator(a) * denominator(b))


def div_frac(a, b):
    return make_frac(numerator(a) * denominator(b), denominator(a) * numerator(b))


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

    print("Good fractions")


if __name__ == "__main__":
    test_frac()
