from __future__ import annotations
class Dog:
	def __init__(self,name: str,size: int):
		self.name = name
		self.size = size
  
	def __gt__(self, other: Dog):
		return self.size > other.size

	def __repr__(self):
		return  f"Dog[name={self.name}, size={self.size}]"

	__str__ = __repr__
		

dogs = [Dog("Grigometh", 200),Dog("Pelusa", 5),Dog("Clifford", 9000)]

print(max(dogs))
print(sorted(dogs))

# Dog[name=Clifford, size=9000]
# [Dog[name=Pelusa, size=5], Dog[name=Grigometh, size=200], Dog[name=Clifford, size=9000]]