def build_less_than_map(L):
    result = {}
    for x in L:
        if x not in result:
            result[x] = []
        for y in L:
            if y < x:
                if y not in result[x]:
                    result[x].append(y)
    return result

L = [4, 1, 3, 3]
m = build_less_than_map(L)
print(m)
