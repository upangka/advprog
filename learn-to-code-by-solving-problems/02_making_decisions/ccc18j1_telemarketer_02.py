# Get four last digits of the telephone number
first, second, third, last = (int(input()) for _ in range(4))


if first in {8, 9} and second == third and last in {8, 9}:
    print("ignore")
else:
    print("answer")
