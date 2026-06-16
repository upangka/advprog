from exercise_04 import matching_predicate
from exercise_09 import zero_or_more
from exercise_07 import reduce

whitespace = reduce(zero_or_more(matching_predicate(str.isspace)), "".join)


def test_whitespace():
    assert whitespace(" ", 0) == (" ", 1)
    assert whitespace("abc", 0) == ("", 0)  # Not an error. Just no whitespace.
    assert whitespace("   ", 0) == ("   ", 3)
    print("Good tests for test_whitespace")

if __name__ == '__main__':
    test_whitespace()
