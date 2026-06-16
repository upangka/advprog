from after import after


def add(x, y):
    print(f"Adding {x} + {y} -> {x + y}")
    return x + y


def help():
    return add(2, 3)


after(3, help)

after(3, lambda: add(2, 3))


import functools

p = functools.partial(add, 2, 3)
print(f"查看已经绑定的参数: f={p.func} args={p.args} kwargs={p.keywords}")

"""
属性	        类型	    内容
p.func	        function   被包装的原始函数
p.args	        tuple	   已绑定的位置参数（按顺序）
p.keywords	    dict	   已绑定的关键字参数
"""

after(3, p)
