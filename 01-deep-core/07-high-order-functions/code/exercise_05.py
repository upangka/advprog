def matching_predicate(predicate):
    def parse(text, index):
        n = index
        while n < len(text) and predicate(text[n]):
            n += 1
        return (text[index:n], n) if n > index else None

    return parse


parse_integer = matching_predicate(str.isdigit)
parse_name = matching_predicate(str.isalpha)


def match_literal(literal):
    def parse(text,index):
        n = index
        if n < len(text) and text[n] == literal:
            return (text[n],n + 1)
        return None
    return parse

parse_equal = match_literal('=')
parse_semi = match_literal(';')

def test_parse_literal():
    assert parse_equal('123', 0) == None    # Doesn't start with '='
    assert parse_equal('=', 0) == ('=', 1)
    assert parse_equal('', 0) == None    # Doesn't start with '='
    assert parse_semi(';', 0) == (';', 1)
    print("Good tests for test_parse_literal")

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
    test_parse_literal()
    # test_parse_setting()
    # test_parse_settings()
