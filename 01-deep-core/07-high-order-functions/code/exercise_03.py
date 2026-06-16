def parse_matching_predicate(text, index, predicate):
    n = index
    while n < len(text) and predicate(text[n]):
        n += 1
    return (text[index:n], n) if n > index else None


def parse_integer(text, index):
    return parse_matching_predicate(text, index, str.isdigit)


def test_parse_integer():
    assert parse_integer("1234 567", 0) == ("1234", 4)
    assert parse_integer("1234 567", 5) == ("567", 8)
    assert parse_integer("abc", 0) == None  # No match
    assert parse_integer("", 0) == None
    print("Good tests for test_parse_integer")


def parse_name(text, index):
    return parse_matching_predicate(text, index, str.isalpha)


def test_parse_name():
    assert parse_name("abc def", 0) == ("abc", 3)
    assert parse_name("abc def", 4) == ("def", 7)
    assert parse_name("123", 0) == None  # No match
    assert parse_name("", 0) == None  # No match (must be at least one digit)
    print("Good tests for test_parse_name")


"""
Your `parse_setting()` and `parse_settings()` functions should work without modification using these functions.
Please copy that code here and verify that it still works.
"""


def parse_setting(text, index):
    m = parse_name(text, index)
    if m is None:
        return None
    name, index = m
    if index >= len(text) or text[index] != "=":
        return None
    index += 1
    m = parse_integer(text, index)
    if m is None:
        return None
    value, index = m
    if index >= len(text) or text[index] != ";":
        return None
    return ((name, int(value)), index + 1)


def test_parse_setting():
    assert parse_setting("name=42;", 0) == (("name", 42), 8)
    assert parse_setting("x", 0) == None
    assert parse_setting("xyz 2", 0) == None  # Missing '='
    assert parse_setting("a=42", 0) == None  # Missing ';' at end
    print("Good Test parse_setting")


def parse_settings(text, index):
    results = []
    while index < len(text):
        m = parse_setting(text, index)
        if m is None:
            break
        r, index = m
        results.append(r)
    return (dict(results), index)


def test_parse_settings():
    assert parse_settings("a=123;b=42;size=99;", 0) == (
        {"a": 123, "b": 42, "size": 99},
        19,
    )
    assert parse_settings("", 0) == ({}, 0)
    assert parse_settings("a=123;b 42;", 0) == ({"a": 123}, 6)
    print("Good Test parse_settings")


if __name__ == "__main__":
    test_parse_integer()
    test_parse_name()
    test_parse_setting()
    test_parse_settings()
