d = {}
d["hello"] = "hi"
d["hello"] = "goodbye"

print(d["hello"])
print(len(d))

if "hello" in d:
 print("\"hello\" in d")

for key in d.keys():
 print(key)