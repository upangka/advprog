n = int(input())

student_ans = [input() for _ in range(n)]
true_ans = [input() for _ in range(n)]

correct = 0
for i in range(n):
    correct += 1 if student_ans[i] == true_ans[i] else 0
print(correct)
