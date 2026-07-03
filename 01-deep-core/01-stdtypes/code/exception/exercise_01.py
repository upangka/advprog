class ApplicationError(Exception):
    pass


def except_1():
    try:
        raise ApplicationError("App opus")
        print("Should not see me :)")
    except ApplicationError:
        # 重新抛出当前异常
        # 保存完整的堆栈信息
        raise


def except_2():
    try:
        raise ApplicationError("App opus")
        print("Should not see me :)")
    except ApplicationError as err:
        # 重新抛出当前异常
        #
        raise err
