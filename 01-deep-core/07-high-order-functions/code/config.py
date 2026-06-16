def parse_integer(text, index):
    n = index
    while n < len(text) and text[n].isdigit():
        n += 1
    return (text[index:n], n) if n > index else None


assert parse_integer("1234 567", 0) == ("1234", 4)
assert parse_integer("1234 567", 5) == ("567", 8)
assert parse_integer("abc", 0) == None  # No match
assert parse_integer("", 0) == None  # No match (must be at least one digit)


def parse_name(text, index):
    n = index
    while n < len(text) and text[n].isalpha():
        n += 1
    return (text[index:n], n) if n > index else None


assert parse_name("abc def", 0) == ("abc", 3)
assert parse_name("abc def", 4) == ("def", 7)
assert parse_name("123", 0) == None  # No match
assert parse_name("", 0) == None  # No match (must be at least one letter)
