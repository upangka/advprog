from exercise_01 import parse_setting


def parse_settings(text, index):
    ...

def test_parse_settings():
    assert parse_settings("a=123;b=42;size=99;", 0) == ({"a": 123, 'b': 42, 'size': 99}, 19)
    assert parse_settings("", 0) == ({}, 0)
    assert parse_settings("a=123;b 42;", 0) == ({"a": 123}, 6)
    print("Good Test parse_setting")


if __name__ == "__main__":
    test_parse_settings()
