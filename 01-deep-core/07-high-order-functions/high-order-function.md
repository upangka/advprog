
1. Functions are data
2. Functions can create functions

# Tech
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

# config.py

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
