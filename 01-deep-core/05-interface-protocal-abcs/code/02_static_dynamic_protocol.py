from typing import Any, Protocol


# Protocol 要求结构一样，静态检查器比如vscode的插件Pylance会检测到
class SupportGetItem(Protocol):
    def __getitem__(self, index: int, /) -> Any: ...


# Structural typing(Static duck typing)
def handle_something(s: SupportGetItem):
    for item in s:
        print(item, end=" ", flush=True)
    return "Yes,a in 's' " if "a" in s else "not include a"


class Vowels:
    def __getitem__(self, index: int, /):
        return "aeiou"[index]


class A:
    pass


handle_something(Vowels())
# handle_something(A()) # type error


# iter方法的静态检查,只要实现了__getitem__方法,iter内置方法就能接受
# @overload
# def iter(object: _GetItemIterable[_T], /) -> Iterator[_T]: ...


# @type_check_only
# class _GetItemIterable(Protocol[_T_co]):
#     def __getitem__(self, i: int, /) -> _T_co: ...

# class SupportsNext(Protocol[_T_co]):
#     def __next__(self) -> _T_co: ...

a = iter(Vowels())
