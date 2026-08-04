from __future__ import annotations
import math

class Planet:
    x: float
    y: float
    mass: float

    def __init__(self,x,y,mass):
        self.x = x
        self.y = y
        self.mass = mass
        
    def distance_to(self,other: Planet):
        return math.sqrt(
            (other.x - self.x) **2 + (other.y - self.y)**2
        )
        
    @staticmethod
    def total_mass(planets: list[Planet]):
        total = 0
        for p in planets:
            total += p.mass
        return total
            
p1 = Planet(5,10,100)
p2 = Planet(1,2,200)
print(p1.distance_to(p2))
print(Planet.total_mass([p1,p2]))