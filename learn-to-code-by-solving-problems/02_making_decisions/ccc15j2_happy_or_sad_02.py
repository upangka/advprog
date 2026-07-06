SAD = ":-("
HAPPY = ":-)"

# How are you :-) doing :-( today :-)?
# happy
line = input()
h, s = 0, 0
# 利用切片来进行统计
for i in range(len(line) - 3):
    if line[i : i + 3] == SAD:
        s += 1
    if line[i : i + 3] == HAPPY:
        h += 1

if not s and not h:
    print("none")
elif s == h:
    print("unsure")
elif s > h:
    print("sad")
elif h > s:
    print("happy")
else:
    raise RuntimeError("Impossible")
