
def capitalize(L: list[str]):
    for idx in range(len(L)):
        L[idx] = L[idx].upper()

L = ["HeLLo", "WoRLd"]
capitalize(L)
print(L)