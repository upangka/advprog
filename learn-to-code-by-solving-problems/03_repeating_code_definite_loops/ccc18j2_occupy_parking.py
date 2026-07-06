# 这里range代表次数
n, yesterday, today = (input() for _ in range(3))

occupied = 0

# 这里的range用作索引下标
for i in range(int(n)):

    # 逻辑与比较
    # if yesterday[i] == today[i] and today[i] == "C":
    # 链式比较
    if yesterday[i] == today[i] == "C":
        occupied += 1

print(occupied)
