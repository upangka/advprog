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


parse_number = choice(parse_decimal, parse_integer)


def test_parse_number():
    assert parse_number("1234", 0) == ("1234", 4)
    assert parse_number("12.34", 0) == ("12.34", 5)
    assert parse_number("abc", 0) == None
    print("Good tests for test_parse_number")


test_parse_number()
