n, yesterday, today = (input() for _ in range(3))

occupied = 0

for y, t in zip(yesterday, today):
    # 链式比较
    if y == t == "C":
        occupied += 1

print(occupied)
