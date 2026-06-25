# 有意思的是这里我们将类命名为小写就像函数一样
# 在用户看来好像和之前的接口一样
class countdown:
    def __init__(self,n):
        self.n = n

    def __iter__(self):
        # copy every time
        n = self.n
        while n > 0:
            yield n
            n -= 1

