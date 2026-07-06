moves = input()
ball_loc = 1

for swap in moves:
    if swap == "A":
        if ball_loc == 1:
            ball_loc = 2
        elif ball_loc == 2:
            ball_loc = 1
    elif swap == "B":
        if ball_loc == 2:
            ball_loc = 3
        elif ball_loc == 3:
            ball_loc = 2
    elif swap == "C":
        if ball_loc == 1:
            ball_loc = 3
        elif ball_loc == 3:
            ball_loc = 1

print(ball_loc)
