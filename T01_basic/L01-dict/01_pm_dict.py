"""Dict pattern matching
list pattern matching
exp = ['lambda',['a','b'],['*',['a','b'],100]]
>>> match exp:
...     case ['lambda',[*params],*body]:
...         print(f"{params=} \n{body=}")
...     case _:
...         raise ValueError()
...
params=['a', 'b']
body=[['*', ['a', 'b'], 100]]
"""

import pytest


def get_creators(record: dict) -> list[str]:
    """
    Pattern Matching is a powerful tool that can process
    records structured like nested mappings and sequence.
    """
    match record:
        case {'type':'book','api':2,'authors':[*names] }: # dict literal
            # here names is a lisit
            assert type(names) is list
            return names
        case {'type':'book','api':1,'author':name }:
            # Here name is a str,and str is a iterable
            # so use [name] instead of list(name)
            return [name]
        case {'type':'movie','director':name}:
            return [name]
        case {'type':'book'}:
            raise ValueError(f"Invalid 'book' record, {record=!r}")
        case _:
            # also raise an error
            raise ValueError("Invalid record")


def test_example():
    # Here add an anthor key 'topic',but still match the case 
    r1 = dict(api=1,author='鲨鱼のJavthon',type='book',topic='Love Python')
    assert get_creators(r1) == ['鲨鱼のJavthon']

    # The order of the keys in the pattern is irrelevant.
    from collections import OrderedDict
    # The order guaranteed by `OrderedDict` is **insertion order**
    # the order in which you insert the keys is exactly the order
    # in which they are stored and displayed
    r2 = OrderedDict(authors="DavidBeazly BruceEckel LucianoRamalho".split(),
                     type='book',
                     api=2)
    assert get_creators(r2) == ["DavidBeazly", "BruceEckel","LucianoRamalho"]


    r3 = {'type':'movie','director':'StevenZhou'}
    assert get_creators(r3) == ['StevenZhou']

    with pytest.raises(ValueError) as err:
        get_creators({"Bla Bla Bla"})
    assert "Invalid" in str(err.value)

    print("Good Example")

test_example()



















