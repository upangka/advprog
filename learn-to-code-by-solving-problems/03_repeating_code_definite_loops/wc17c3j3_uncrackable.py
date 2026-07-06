from string import ascii_letters, digits

candidates = ascii_letters + digits

password = input()


class PasswordError(Exception):
    pass


def require(condition):
    if not condition:
        raise PasswordError()


def every_char_in_candidates(password):
    for c in password:
        if c not in candidates:
            return False
    return True


def at_least_three_lowercase(password):
    nums = 0
    for c in password:
        if c.islower():
            nums += 1
    return nums >= 3


def at_least_two_uppercase(password):
    nums = 0
    for c in password:
        if c.isupper():
            nums += 1
    return nums >= 2


def at_least_one_digit(password):
    nums = 0
    for c in password:
        if c.isdigit():
            nums += 1
    return nums >= 1


try:
    require(8 <= len(password) <= 12)
    require(every_char_in_candidates(password))
    require(at_least_three_lowercase(password))
    require(at_least_two_uppercase(password))
    require(at_least_one_digit(password))
    print("Valid")
except PasswordError:
    print("Invalid")
