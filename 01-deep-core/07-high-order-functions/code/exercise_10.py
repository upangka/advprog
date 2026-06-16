from exercise_04 import matching_predicate, parse_integer, parse_name
from exercise_09 import zero_or_more
from exercise_07 import reduce, sequence

whitespace = reduce(zero_or_more(matching_predicate(str.isspace)), "".join)


def test_whitespace():
    assert whitespace(" ", 0) == (" ", 1)
    assert whitespace("abc", 0) == ("", 0)  # Not an error. Just no whitespace.
    assert whitespace("   ", 0) == ("   ", 3)
    print("Good tests for test_whitespace")


def token(parser):
    return reduce(
        sequence(whitespace, parser),
        lambda r: r[1],
    )


def test_token():
    assert token(parse_integer)("123", 0) == ("123", 3)
    assert token(parse_integer)("    123", 0) == (
        "123",
        7,
    )  # Leading whitespace ignored
    print("Good tests fro test_token")


if __name__ == "__main__":
    test_whitespace()
    test_token()
