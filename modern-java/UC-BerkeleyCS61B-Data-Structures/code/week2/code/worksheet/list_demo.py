lst = []
lst.append("zero")
lst.append("one")

lst[0] = "zed"
print(len(lst))

if 'one' in lst:
    print("one in lst")
    
for elem in lst:
    print(elem)