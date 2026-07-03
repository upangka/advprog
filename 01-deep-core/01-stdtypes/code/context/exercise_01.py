class Manager:
    def __init__(self, x):
        self.x = x

    def __enter__(self):
        print("enter...")
        # 返回一个列表
        return [1, 3, 5]

    def __exit__(self, ty, val, tb):
        print("exit...")


m = Manager(3)
with m as l:
    # with Manager(3) as l:
    for i in l:
        print(i)

print("Over...")
