password = input()
lower_count = 0
upper_count = 0
digit_count = 0

for strength in password:
    if strength.islower():
        lower_count = lower_count + 1
    if strength.isupper():
        upper_count = upper_count + 1
    if strength.isdigit():
        digit_count = digit_count + 1

if len(password) <= 7 or len(password) > 12:
    print("Invalid")
elif lower_count >= 3 and upper_count >= 2 and digit_count >= 1:
    print("Valid")
else:
    print("Invalid")
