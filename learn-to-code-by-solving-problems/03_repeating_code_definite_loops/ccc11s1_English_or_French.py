n = int(input())
txt = "\n".join([input() for _ in range(n)])
# 处理成lower
lower_txt = txt.lower()

t_count = lower_txt.count("t")
s_count = lower_txt.count("s")

language = "French" if s_count >= t_count else "English"
print(language)
