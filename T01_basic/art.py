"""
Print the ASCII art 
Just two nested loops, but I think the output is really interesting

https://github.com/dabeaz-course/python-mastery/blob/main/Exercises/ex1_1.md
"""
import sys
import random

symbols = r'\|/'


def generate_row(columns):
    return "".join([random.choice(symbols) for _ in range(columns)])

def draw(row,columns):
    """core logic"""
    for _ in range(row):
        print(generate_row(columns))
        


if __name__ == "__main__":
    # handle input
    if len(sys.argv) != 3:
        raise SystemExit("Usage: uv run art.py 10 20")
    draw(int(sys.argv[1]),int(sys.argv[2]))

