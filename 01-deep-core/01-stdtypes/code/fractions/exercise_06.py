from __future__ import annotations


def gcd(a, b):
    # Greatest common divisor
    while b:
        a, b = b, a % b
    return a


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
