word = input().strip()
count = 0
start = 0

while True:
    # Find H
    h = word.find("H", start)
    if h == -1:
        break
    # Find O after H
    o = word.find("O", h + 1)
    if o == -1:
        break
    # Find N after O
    n = word.find("N", o + 1)
    if n == -1:
        break
    # Find I after N
    i = word.find("I", n + 1)
    if i == -1:
        break
    # Found a complete HONI block
    count += 1
    start = i + 1  # continue after this I

print(count)
