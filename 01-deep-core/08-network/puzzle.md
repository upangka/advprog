
Graydon decides that he's going to go visit his friend Fletcher. He arrives at the apartment building and gets in the elevator. However, instead of a typical building directory, the following puzzle is posted:

Baker, Cooper, Fletcher, Miller, and Smith live here. They each live on a different floor. Baker does not live on the top floor. Cooper does not live on the bottom floor. Fletcher does not live on either the top or the bottom floor. Miller lives on a higher floor than does Cooper. Smith does not live on a floor adjacent to Fletcher. Fletcher does not live on a floor adjacent to Cooper.

"What button do I press?", he asks.

# Exercise 1 - Brute force

Write a program that finds out where everyone lives.

To do this, think about a brute force solution. You know that Baker, Cooper, Fletcher, Miller, and Smith all live on different floors.
One way to solve it would be to cycle through all permutations of the floors and to enforce the various rules as a series of constraints. Print out the solutions that pass them all.

Is there only one solution? Multiple solutions? No solutions?

```python
def brute_force():
    for baker in range(1, 6):
        for cooper in range(1, 6):
            for fletcher in range(1, 6):
                for miller in range(1, 6):
                    for smith in range(1, 6):
                        # All must live on different floors.
                        if len({baker, cooper, fletcher, miller, smith}) != 5:
                            continue
                        # Enforce additional constraints and throw out bad solutions
                        if baker == 5:
                            continue
                        # ... more constraints ...
                        # Print a solution
                        print(f'{baker}={cooper}={fletcher}={miller}={smith}')
```