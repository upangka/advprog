def add_frac(a, b):
    return (a[0] * b[1] + a[1] * b[0], a[1] * b[1])


def sub_frac(a, b):
    return (a[0] * b[1] - a[1] * b[0], a[1] * b[1])


def mul_frac(a, b):
    return (a[0] * b[0], a[1] * b[1])


def div_frac(a, b):
    return (a[0] * b[1], a[1] * b[0])
