P = int(input())  # 总过多少升油漆
B = int(input())  # 每个徽章用掉多少升油漆
D = int(input())  # 每个徽章卖多少钱

badges, left_over = divmod(P, B)

amount = badges * D + left_over
print(amount)
