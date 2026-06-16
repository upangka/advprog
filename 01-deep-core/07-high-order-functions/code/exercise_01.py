from config import parse_integer, parse_name


def parse_setting(text, index):
    values = []
    m = parse_name(text, index)
    if m is None:
        return None
    value, n = m
    values.append(value)
    if n >= len(text) or text[n] != "=":
        return None
    n += 1
    m = parse_integer(text, n)
    if m is None:
        return None
    value, n = m
    values.append(int(value))
    if n >= len(text) or text[n] != ";":
        return None
    return (tuple(values), n + 1)


def test_parse_setting():
    assert parse_setting("name=42;", 0) == (("name", 42), 8)
    assert parse_setting("x", 0) == None
    assert parse_setting("xyz 2", 0) == None  # Missing '='
    assert parse_setting("a=42", 0) == None  # Missing ';' at end
