# @contextmanager

```sh
>>> @contextlib.contextmanager
... def f():
...     print("enter f")
...     yield 1
...     print("exit f")

>>> f()
<contextlib._GeneratorContextManager object at 0x0000025EF2B7F8C0>

>>> with f():
...     print("running")
...     
enter f
running
exit f
```

# ExitStack

`ExitStack` solves the problem of **dynamic resource management**.For example:
就像Java里面的reentrantlock
- You don't know in advance how many files you need to open, or which resources you need to open.
- You want to add resources inside a loop, but you want all of them to be released together at the end.
- You want to split the resource management logic across different functions, but still clean everything up in one unified place.

```sh
>>> from contextlib import contextmanager,ExitStack
>>> @contextlib.contextmanager
... def f(flag):
...     print(f"enter f({flag})")
...     yield 
...     print(f"exit f({flag})")
...     
>>> st = ExitStack()
>>> st.enter_context(f(1))
enter f(1)
>>> st.enter_context(f(2))
enter f(2)
>>> st.close()
exit f(2)
exit f(1)
```
