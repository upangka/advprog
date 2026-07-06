composition = input()
separator = False
accents = composition[0]
C = 0
A = 0

for char in composition:
    if char == "|":
        separator = True
    elif separator == True:
        accents += char
        separator = False

for i in accents:
    if i == "A" or i == "D" or i == "E":
        A += 1
    elif i == "C" or i == "F" or i == "G":
        C += 1

if A > C:
    print("A-mol")
elif C > A:
    print("C-dur")
else:
    last = composition[len(composition) - 1]
    if last == "A" or last == "D" or last == "E":
        print("A-mol")
    elif last == "C" or last == "F" or last == "G":
        print("C-dur")
