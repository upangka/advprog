from exercise_05 import parse_equal, parse_integer, parse_name, parse_semi


def sequence(*parsers):
    def parse(text, index):
        results = []
        for f in parsers:
            if (m := f(text, index)) is None:
                return None
            val, index = m
            results.append(val)
        return (results, index)

    return parse


match_setting = sequence(parse_name, parse_equal, parse_integer, parse_semi)


def test_match_setting():
    assert match_setting("x=42;", 0) == (["x", "=", "42", ";"], 5)
    assert match_setting("x=42", 0) == None  # Missing ;
    assert match_setting("x_42", 0) == None  # Missing =
    print("Good tests for test_match_setting")


if __name__ == "__main__":
    test_match_setting()
