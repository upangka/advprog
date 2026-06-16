from config import parse_integer, parse_name


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


if __name__ == "__main__":
    test_parse_setting()
