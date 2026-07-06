# 每月套餐，已经使用套餐的月数
monthly_mb, n = (int(input()) for _ in range(2))

# 总量total
total = (n + 1) * monthly_mb
# 控制输入次数
usage = sum(int(input()) for _ in range(n))
print(total - usage)
