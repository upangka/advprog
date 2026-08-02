
class Dog:
    def __init__(self,name,size):
        self.name = name
        self.size = size
        
    def grow(self):
        self.size += 1
        

    def __str__(self):
        return f"{self.name} the size {self.size} dog"
    
dogs = [Dog("maya", 1000), Dog("yipster", 5), Dog("scott", 25)]
print(dogs[0])