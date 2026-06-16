import pytest


@pytest.mark.parametrize(
    "args,expect",
    [
        ([1, 3], 3),
        ([3, 1], 3),
        ([30, 10, 20], 30),
    ],
)
def test_max_args(args, expect):
    print(f"\n{args=} {expect=}", end="", flush=True)
    result = max(args)
    assert result == expect
