monthly_mb, n = (int(input()) for _ in range(2))

excess = 0

for _ in range(n):
    usage = int(input())
    excess += monthly_mb - usage

print(excess + monthly_mb)
