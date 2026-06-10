# int consistent with complex
def real_imag(x: complex):
    print(x.real, x.imag)


real_imag(1 + 2j)
real_imag(1)
print(issubclass(type(1), type(1 + 2j)))
