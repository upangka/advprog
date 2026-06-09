

"""
给定一个类型别名，表示一个字典，键为字符串，值为字符串或整数。
缺点：无法限制字典必须包含特定的键，例如"name"和"age"
"""

# from typing import TypeAlias
# Person: TypeAlias = dict[str, str | int]
# bob: Person = {"name": "Pkmer", "age": 25}


"""
限定字典必须包含特定的键，例如"name"和"age"
定义了字典的结构
"""

# from typing import TypedDict
# class Person(TypedDict):
#     name: str
#     age: int

# bob: Person = {"nameee": "Bob", "age": 25,"city": "ShenZhen, China"}


"""
total 控制字典是否必须包含所有键
"""

from typing import TypedDict
class Person(TypedDict, total=True):
    name: str
    age: int
    city: str


bob: Person = {"name": "Bob", "age": 25}
