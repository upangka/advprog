"""
递推问题
"""
press_times = int(input())

a_nums = 1
b_nums = 0

for _ in range(press_times):
    a_nums, b_nums = b_nums, a_nums + b_nums

print(a_nums, b_nums)
