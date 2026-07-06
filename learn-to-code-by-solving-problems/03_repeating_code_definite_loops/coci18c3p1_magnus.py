word = input()

idx, count = 0, 0
TARGET = "HONI"

for c in word:
    if c == TARGET[idx]:
        idx += 1
        if idx == len(TARGET):
            idx = 0
            count += 1
print(count)
