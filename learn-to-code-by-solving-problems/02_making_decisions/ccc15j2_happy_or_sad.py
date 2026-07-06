SAD = ":-("
HAPPY = ":-)"

line = input()

sad_nums = line.count(SAD)
happy_nums = line.count(HAPPY)

answer = ""

if sad_nums > happy_nums:
    answer = "sad"
elif sad_nums == happy_nums:
    if sad_nums == 0:
        answer = "none"
    else:
        answer = "unsure"
elif happy_nums > sad_nums:
    answer = "happy"
else:
    raise RuntimeError("Impossiable")

print(answer)
