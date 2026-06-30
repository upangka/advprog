import inspect


def with_init(cls):
    """代码生成技术"""
    annos = cls.__annotations__
    args_vals = ", ".join([f"{key}: {value.__name__}" for key, value in annos.items()])
    init_code = f"def __init__(self, {args_vals}):\n"
    init_code += "\n".join([f"\tself.{k} = {k}" for k in annos]) + "\n"

    print(init_code)
    locs = {}
    exec(init_code, locs)

    args_vals = ", ".join([f"self.{k}" for k in annos])
    repr_code = f"def __repr__(self): \n"
    repr_code += (
        f"\treturn f'{cls.__name__}({args_vals})\t 【Generate BY 鲨鱼のJavthon】'"
    )
    print(repr_code)
    exec(repr_code, locs)

    print(locs.keys())
    init_m = locs["__init__"]
    repr_m = locs["__repr__"]
    print(inspect.signature(init_m))

    # monkey patching
    cls.__init__ = init_m
    cls.__repr__ = repr_m

    return cls


@with_init
class Point:
    x: int
    y: int

"""
$ uv run python -i exercise_01.py
def __init__(self, x: int, y: int):
        self.x = x
        self.y = y

def __repr__(self):
        return f'Point(self.x, self.y)   【Generate BY 鲨鱼のJavthon】'
dict_keys(['__builtins__', '__init__', '__repr__'])
(self, x: int, y: int)
>>> Point(1,3)
Point(self.x, self.y)    【Generate BY 鲨鱼のJavthon】
"""
