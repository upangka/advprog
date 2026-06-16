

# Tech

1. Functions are data
2. Functions can create functions

## Functions are data

```python
>>> def f(x):
...     return x * 2
...
>>> items = [1,2,f]
>>> items
[1, 2, <function f at 0x7f18290777e0>]
>>> items[2](3)
6
```

## Functions can create functions

```python
>>> def make_adder(x):
...     def add(y):
...         return x + y
...     return add
...
>>> add5 = make_adder(5)
>>> add5(100)
105
>>> add100 = make_adder(100)
>>> add100(80)
180
```

# Parse Setting

Objective: Explore an application of higher-order functions and composition of functions.

---

Ben has been lamenting the complex state of packaging, configuration, and software deployment. "I just can't take it anymore!!!!" he wails as he runs out of the office.

Clearly, the solution to all of Ben's problem, is for Ben to create a new packaging tool based on a new simplified configuration file format (see https://xkcd.com/927/).

Thus, Ben has been hard at work coding a tool to read his new configuration file format. Part of it involves text parsing(Ben 这个配置工具的一部分工作涉及文本解析). He needs to write code that recognizes various elements from a text string such as numbers and names:

- integers - Example: 123
- names - Example: abcdef

To do this, he's written the following functions. Each of these functions accept an input string and integer starting index as input. They produce a tuple of the matching text and an ending index as output or None if there is no match.

Note: This interface may look a little weird, but Ben is thinking about the problem of efficiently reading through a text file without having to make a lot of string copies. Keeping the original unmodified text and a numeric index seemed like one way to do it.

- lamenting /ləˈmen.tɪŋ/ v. 哀叹；悲叹；抱怨（指对某种困难、不公或糟糕的状况表达悲痛、不满或失望，语气比 complain 更重，带有一种无可奈何的悲愤感）
- weird /wɪrd/ adj. 奇怪的；怪异的；不寻常的（指某事物与通常的预期或习惯不同，让人感到困惑或意外。在上下文中并非贬义，更多是 Ben 对自己接口设计的一种自嘲式评价，暗示这种设计不符合直觉，但有合理的性能考量）

[config.py](./code/config.py)

```python
def parse_integer(text, index):
    n = index
    while n < len(text) and text[n].isdigit():
       n += 1
    return (text[index:n], n) if n > index else None


assert parse_integer("1234 567", 0) == ("1234", 4)
assert parse_integer("1234 567", 5) == ("567", 8)
assert parse_integer("abc", 0) == None  # No match
assert parse_integer("", 0) == None  # No match (must be at least one digit)
```

```python
def parse_name(text, index):
    n = index
    while n < len(text) and text[n].isalpha():
        n += 1
    return (text[index:n], n) if n > index else None


assert parse_name("abc def", 0) == ("abc", 3)
assert parse_name("abc def", 4) == ("def", 7)
assert parse_name("123", 0) == None  # No match
assert parse_name("", 0) == None  # No match (must be at least one letter)
```

## Exercise 1 - The Parser

[exercise_01.py](./code/exercise_01.py)

As part of Ben's configuration file format, configuration settings are
written in the following form:

`name=value;`

For example:

`x=42;`

Your task, use the functions above to write a `parse_setting()`
function that converts text such as 'x=42;' into a tuple ('x', 42).
Integer values should be converted to a Python integer. Like the
`parse_integer()` and `parse_name()` functions, the ending index
will also be returned. If there is any error in the format (such as
a missing semicolon), the function should return None.

```python
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
```

## Exercise 2 - The Repetitive

[exercise_02.py](./code/exercise_02.py)

The `parse_setting()` function in exercise 1 is only a small part of a larger parser. Ben actually wants to parse multiple settings into a Python dictionary. For example, input like this:

`a=123;b=42;size=99;`

Should turn into the following:

`{'a': 123, 'b': 42, 'size': 99 }`

Like the other functions, the ending index will also be returned. If no settings can be parsed, an empty dictionary is returned.

You task: implement a `parse_settings()` function that repeatedly calls `parse_setting()` to parse each setting one at a time and returns a dictionary when no more settings can be found.

Note: You can turn a list of tuples `[('a',123), ('b', 42), ('size', 99)]` into a dict using the `dict([('a',123), ('b', 42), ...])`.

```python
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

```


## Exercise 3 - The Repetitive (Code)

[exercise_03.py](./code/exercise_03.py)

Ben shows his code to Arjoon who notices that the `parse_integer()` and `parse_name()` functions are basically identical.

"There's just one tiny difference in both of the functions", he remarks.

Perhaps the core functionality could be implemented in a single function that accepts some kind of extra predicate function for testing characters. For example:

Generic parsing function

```python
def parse_matching_predicate(text, index, predicate):
    n = index
    while n < len(text) and predicate(text[n]):
        n += 1
    return (text[index:n], n) if n > index else None


def parse_integer(text, index):
    return parse_matching_predicate(text, index, str.isdigit)

def parse_name(text, index):
    return parse_matching_predicate(text, index, str.isalpha)
```

相比通用的代码，之前的代码[#parse-setting](#parse-setting)

## Exercise 4 - The (Code) Generator

[exercise_04.py](./code/exercise_04.py)

Looking at [Exercise 3](#exercise-3---the-repetitive-code), it all still feels a bit clunky. Yes,
there's the more general `parse_matching_predicate()` function, but
there are now these tiny functions such as `parse_integer()` that
merely provide some kind of wrapper around it.

All of this might be simplified if the `parse_matching_predicate()`
function was re-envisioned. Instead of directly parsing the
supplied text, what if it created a parsing function instead?

```python
def matching_predicate(predicate):
    def parse(text, index):
        n = index
        while n < len(text) and predicate(text[n]):
            n += 1
        return (text[index:n], n) if n > index else None
    return parse
```

Using this code generator, show how you could define `parse_integer`

- clunky /klʌŋki/ adj. 笨重的；笨拙的；
- re-envisioned /ˌriː.ɪnˈvɪʒ.ənd/ v. 重新构想；重新设想

```python
def matching_predicate(predicate):
    def parse(text,index):
        n = index
        while n < len(text) and predicate(text[n]):
            n += 1
        return (text[index:n], n) if n > index else None
    return parse

parse_integer = matching_predicate(str.isdigit)
parse_name = matching_predicate(str.isalpha)
```
对比[exercise-3](#exercise-3---the-repetitive-code),`parse_integer`和`parse_name`定义的变化

---

## Exercise 5 - The Literal

[exercise_05.py](./code/exercise_05.py)

Let's revisit the `parse_setting()` function. In that function, there should be calls to `parse_name()` and `parse_integer()`.
However, there should also be a check for the `literal` `=` and `;` characters. You may have coded these checks directly, but maybe they too could be generalized by a function as well.

Write a function `match_literal(literal)` that *creates* a function that matches an exact sequence of supplied text:

```python
def match_literal(literal):
    def parse(text, index):
        if index < len(text) and text[index] == literal:
            return (text[index], index + 1)
        return None

    return parse
```

Examples

```python
parse_equal = match_literal("=")
parse_semi = match_literal(";")
```

对比[exercise-1---the-parser](#exercise-1---the-parser),重新设计`parse_setting`

```python
def parse_setting(text, index):
    if not (m := parse_name(text, index)):
        return None
    name, index = m
    if (m := parse_equal(text, index)) is None:
        return None
    _, index = m
    if not (m := parse_integer(text, index)):
        return None
    value, index = m
    if not (m := parse_semi(text, index)):
        return None
    _, index = m
    return ((name, int(value)), index)
```

```python
assert parse_setting("name=42;", 0) == (("name", 42), 8)
assert parse_setting("x", 0) == None
assert parse_setting("xyz 2", 0) == None  # Missing '='
assert parse_setting("a=42", 0) == None  # Missing ';' at end
```


## Exercise 6 - The Sequence

[exercise_06.py](./code/exercise_06.py)

If you look more closely at Exercise 5, you'll find that the
`parse_setting()` function is stepping through a sequence of
individual parsing steps, but the code looks almost identical at
each step. Maybe this sequencing is something that could be
generalized in some way.

Create a function `sequence(*parsers)` that accepts any number of
parser functions and combines them into a single function that
invokes each parser in order. If any parser fails, return None. If
all parsers work, return a list of the matching parts and an ending
index.

> 因为有相同的接口与返回值，这里直接构建成`sequence`来取代[exercise 5](#exercise-5---the-literal)的 `parse_setting`

```python
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
```

Commentary: The `match_setting()` function is not quite the same
as the previous `parse_setting()` function. For one, it doesn't
return the answer in the right form. It also includes extra
information such as the `=` and `;` characters. Finally, it
doesn't properly convert the string value '42' into a Python
integer. We will fix this in the next exercise. Proceed to ex7.

也可以玩的更花里花哨

```python
>>> match_setting = sequence(sequence(parse_name, parse_equal), sequence(parse_integer, parse_semi))
>>> match_setting("x=42;", 0)
([['x', '='], ['42', ';']], 5)
```

## Exercise 7 - The Reduction

[exercise_07.py](./code/exercise_07.py)

In exercise 6, the `match_setting()` function *almost* does what you need
to replace the functionality of `parse_setting()`. However, the return value
result includes unwanted information. For example, instead of getting
just the name and the value, you also get the `'='` and `';'` characters.
How would you discard them?


One way to fix it would be to apply some kind of extra operation to
the final output. For example, you could wrap the `match_setting()`
function with some other function that extracted the interesting parts.

```python
def parse_setting(text, index):
    if not (mat := match_setting(text,index)):
        return None
    parts, n = mat
    return ((parts[0], int(parts[2])), n)
```

Yes, this works, but it feels a bit clumsy. Maybe it could also be
generalized in some way. Perhaps a `reduce()` function can be
defined that takes a parser and a function as input. As output,
it returns the result of applying the function to the raw parsing
data.

```python
def reduce(parser, func):
    def parse(text, index):
        if (m := parser(text, index)) is None:
            return None
        val, index = m
        return (func(val), index)

    return parse
```

Here's an example showing how `reduce()` might work with integers.

```python
assert parse_integer('123', 0) == ('123', 3)
assert reduce(parse_integer, int)('123', 0) == (123, 3)
```

Show how you could define `parse_setting()` in terms of `reduce()` and
`sequence()`. Show that it passes all of the earlier tests.
Hint: Use of a lambda function might be useful here.

```python
parse_setting = reduce(
    sequence(parse_name, parse_equal, reduce(parse_integer, int), parse_semi),
    lambda r: (r[0], r[2]),
)

assert parse_setting("name=42;", 0) == (("name", 42), 8)
assert parse_setting("x", 0) == None
assert parse_setting("xyz 2", 0) == None  # Missing '='
assert parse_setting("a=42", 0) == None  # Missing ';' at end
```

非常灵活的函数组合: 归功于统一的接口

```python
>>> parse_setting = reduce(
...     sequence(
...         sequence(parse_name,parse_equal),
...         sequence(reduce(parse_integer,int),parse_semi)
...     ),
...     lambda r: (r[0][0],r[1][0])
... )
>>> parse_setting("name=42;", 0)
(('name', 42), 8)
```

Challenge: Show how you can use a combination of reduce,
parse_integer(), and sequence to parse a decimal number. A decimal
number consists of a series of digits, decimal point (.) and more
digits.

> 非常巧妙，通过组装函数得到新的函数

```python
parse_decimal = reduce(
    sequence(parse_integer, match_literal("."), parse_integer),
    ''.join
)

assert parse_decimal("123.45", 0) == ("123.45", 6)
assert parse_decimal("123", 0) == None
```


## Exercise 8 - The Choice

[exercise_08.py](./code/exercise_08.py)

The `sequence()` function you wrote works by applying parsers in
order, requiring all to match. A different kind of strategy is to
apply parsers in order, but stopping when a match is found. This
allows you to test for alternatives.

Define the function `choice()` that allows you to choose between
different alternatives--picking the first one that matches.

```python
def choice(*parsers):
    def parse(text, index):
        ... # You define
    return parse
```

Here's an example that shows how to use `choice()` to parse either a
decimal or an integer number using the previously written
`parse_integer()` and `parse_decimal()` functions.

```python
parse_number = choice(parse_decimal, parse_integer)

def test_parse_number():
    assert parse_number("1234", 0) == ('1234', 4)
    assert parse_number("12.34", 0) == ('12.34', 5)
    assert parse_number("abc", 0) == None

test_parse_number()
```

Show how you could use a combination of choice(), reduce(), parse_integer(),
and parse_decimal() functions to convert numeric values into an appropriate
Python type while parsing.

```python
parse_converted_number = ... # You define

def test_parse_converted_number():
    assert parse_converted_number("1234", 0) == (1234, 4)    # Note: int
    assert parse_converted_number("12.34", 0) == (12.34, 5)    # Note: float
    assert parse_converted_number("abc", 0) == None

test_parse_converted_number()    # Uncomment
```

Define a more powerful version of `parse_setting()` that handles both
kinds of numbers.

```python
parse_setting = ... # You define

# New test, with different numeric types and conversion
def test_parse_setting():
    assert parse_setting("x=123;", 0) == (('x', 123), 6)
    assert parse_setting("y=1.3;", 0) == (('y', 1.3), 6)
    assert parse_setting("x 42", 0) == None  # Missing =
    assert parse_setting("x=42", 0) == None  # Missing ;
    assert parse_setting("x=y;", 0) == None  # y is not a number

test_parse_setting()    # Uncomment
```