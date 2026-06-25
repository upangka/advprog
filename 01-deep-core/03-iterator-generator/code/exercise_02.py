def countdown_bug(n):
    """如果有清理操作，验证不会执行"""
    print(f"Count down from {n}")

    while n > 0:
        yield n
        n -= 1
    print("Do some clearn work")



def countdown(n):
    print(f"Count down from {n}")

    try:
        while n > 0:
            yield n
            n -= 1
    except Exception:
        # 只是消耗部分生成器，不代表异常
        print("Run here???")
    finally:
        # do some clear up operation
        print(f"Only made it to {n}")


if __name__ == '__main__':
    for x in countdown_bug(3):
        if x == 2:
            break
        print(x)
