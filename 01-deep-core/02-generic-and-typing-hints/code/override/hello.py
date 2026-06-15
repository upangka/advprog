"""
Happy Coding :-)
I've been learning Python for a few days.
Today I learned about @overload.
"""

from typing import overload,Iterable

@overload
def hello_world(msg: str,/) -> str:...
@overload
def hello_world(msgs: Iterable[str],/) -> str:...
def hello_world(text):
    return text if isinstance(text,str) else "\n~ ".join(text)


f = lambda context: print(hello_world(context))

f("Hello world")
f(["Hi ,","I'm currently learning Python too.","Fingers Crossed","From ShenZhen, China."])