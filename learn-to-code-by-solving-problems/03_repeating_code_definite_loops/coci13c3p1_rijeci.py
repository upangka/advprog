"""
当数据过大，会内存移除发生错误
不要生成完整的字符串，只追踪 A 和 B 的数量变化！这是处理大规模递推问题的关键。 🎯
"""

press_times = int(input())

letters = "A"

for _ in range(press_times):
    container = []
    for letter in letters:
        if "A" == letter:
            # container.append("B")
            container.append("B")
        elif "B" == letter:
            container.append("BA")
    # update letters
    letters = "".join(container)

print(letters.count("A"), letters.count("B"))
