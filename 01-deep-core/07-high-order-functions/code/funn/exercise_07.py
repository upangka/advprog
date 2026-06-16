from exercise_05 import (
    parse_equal,
    parse_integer,
    parse_name,
    parse_semi,
    match_literal,
)
from exercise_06 import sequence


def reduce(parser, func):
    def parse(text, index):
        if (m := parser(text, index)) is None:
            return None
        val, index = m
        return (func(val), index)

    return parse


assert parse_integer("123", 0) == ("123", 3)
assert reduce(parse_integer, int)("123", 0) == (123, 3)


parse_setting = reduce(
    sequence(parse_name, parse_equal, reduce(parse_integer, int), parse_semi),
    lambda r: (r[0], r[2]),
)


def test_parse_setting():
    assert parse_setting("name=42;", 0) == (("name", 42), 8)
    assert parse_setting("x", 0) == None
    assert parse_setting("xyz 2", 0) == None  # Missing '='
    assert parse_setting("a=42", 0) == None  # Missing ';' at end
    print("Good Test parse_setting")


parse_decimal = reduce(
    sequence(parse_integer, match_literal("."), parse_integer), "".join
)


def test_parse_decimal():
    assert parse_decimal("123.45", 0) == ("123.45", 6)
    assert parse_decimal("123", 0) == None


if __name__ == "__main__":
    test_parse_setting()
    test_parse_decimal()
