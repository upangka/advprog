# Get four last digits of the telephone number
last_4digits = [int(input()) for _ in range(4)]


class TeleMarketerError(Exception):
    pass


def require(condition):
    if not condition:
        raise TeleMarketerError


try:
    # 第一位数字是8或者9
    require(last_4digits[0] in {8, 9})
    # 第二位和第三位数字相同
    require(last_4digits[1] == last_4digits[2])
    # 第四位数字是8或者9
    require(last_4digits[-1] in {8, 9})

    # 满足所有条件，是电话推销
    print("ignore")
except TeleMarketerError:
    # 正常电话接听
    print("answer")
