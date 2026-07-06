n = int(input())

t_count, s_count = 0, 0
# 相比收集全部字符串再处理
# 得到一次就处理一次会比较节省内存吧
for _ in range(n):
    txt = input().lower()
    t_count += txt.count("t")
    s_count += txt.count("s")

print("French" if s_count >= t_count else "English")
