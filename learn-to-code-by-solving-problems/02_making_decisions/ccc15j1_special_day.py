month, day = (int(input()) for _ in range(2))


# 假设按天数算
target = 2 * 30 + 18
days = month * 30 + day

if target == days:
    print("Special")
elif target < days:
    print("After")
elif target > days:
    print("Before")
else:
    RuntimeError("Impossiable")
