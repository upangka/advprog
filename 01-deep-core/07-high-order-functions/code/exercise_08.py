from exercise_05 import (
    parse_equal,
    parse_integer,
    parse_name,
    parse_semi,
    match_literal,
)
from exercise_06 import sequence
from exercise_07 import parse_decimal, reduce


def choice(*parsers):
    def parse(text, index):
        for parser in parsers:
            if m := parser(text, index):
                return m
        return None

    return parse


# 注意顺序，先解析decimal
parse_number = choice(parse_decimal, parse_integer)


def test_parse_number():
    assert parse_number("1234", 0) == ("1234", 4)
    assert parse_number("12.34", 0) == ("12.34", 5)
    assert parse_number("abc", 0) == None
    print("Good tests for test_parse_number")


parse_converted_number = choice(
    reduce(parse_decimal, float), reduce(parse_integer, int)
)


def test_parse_converted_number():
    assert parse_converted_number("1234", 0) == (1234, 4)  # Note: int
    assert parse_converted_number("12.34", 0) == (12.34, 5)  # Note: float
    assert parse_converted_number("abc", 0) == None
    print("Good tests for test_parse_converted_number")


if __name__ == "__main__":
    test_parse_number()
    test_parse_converted_number()
