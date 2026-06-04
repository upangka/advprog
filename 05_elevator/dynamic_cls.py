

class Example:
    def yow(self):
        print(f"yow...{self.x}")

class Blah:
    def __init__(self,x):
        self.x = x

    def blah(self):
        print(f"blah...{self.x}")

b = Blah(6)
b.blah()
# change the class at runtime
b.__class__ = Example
b.yow()  # amazing!!! access to x 6

# back,still access x = 6
b.__class__ = Blah
b.blah()

"""output
blah...6
yow...6
blah...6
"""
