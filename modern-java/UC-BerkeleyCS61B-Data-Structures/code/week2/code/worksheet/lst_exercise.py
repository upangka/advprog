
def common(L1: list[int],L2: list[int]):
    ret = []
    for item in L1:
        if item in L2 and item not in ret:
            ret.append(item)
    return ret

L1 = [1, 2, 3, 4]
L2 = [3, 4, 5, 6]

print(common(L1,L2))