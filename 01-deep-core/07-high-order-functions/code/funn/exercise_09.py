from exercise_08 import parse_setting, reduce


def zero_or_more(parser):
    def parse(text, index):
        results = []
        while m := parser(text, index):
            value, index = m
            results.append(value)
        return (results, index)

    return parse


parse_settings = zero_or_more(parse_setting)


def test_parse_settings():
    assert parse_settings("speed=42;size=9.5;maxspeed=1000;", 0) == (
        [("speed", 42), ("size", 9.5), ("maxspeed", 1000)],
        32,
    )
    assert parse_settings("", 0) == ([], 0)
    print("Good tests for test_parse_settings")


parse_settings_dict = reduce(parse_settings, dict)


def test_parse_settings_dict():
    assert parse_settings_dict("speed=42;size=9.5;maxspeed=1000;", 0) == (
        {"speed": 42, "size": 9.5, "maxspeed": 1000},
        32,
    )
    assert parse_settings_dict("", 0) == ({}, 0)
    print("Good tests for test_parse_settings_dict")


if __name__ == "__main__":
    test_parse_settings()
    test_parse_settings_dict()
