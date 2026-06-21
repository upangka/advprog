
Graydon decides that he's going to go visit his friend Fletcher. He arrives at the apartment building and gets in the elevator. However, instead of a typical building directory, the following puzzle is posted:

Baker, Cooper, Fletcher, Miller, and Smith live here. They each live on a different floor. Baker does not live on the top floor. Cooper does not live on the bottom floor. Fletcher does not live on either the top or the bottom floor. Miller lives on a higher floor than does Cooper. Smith does not live on a floor adjacent to Fletcher. Fletcher does not live on a floor adjacent to Cooper.

1. 楼层编号: 这栋楼有 5 层,从下到上编号为 1 到 5(1 是底层,5 是顶层).
2. 互不相同: Baker、Cooper、Fletcher、Miller、Smith 五个人分别住在不同的楼层(没有两个人住在同一层).
3. Baker(贝克): 不住在顶层(即不住在 5 楼).
4. Cooper(库珀): 不住在底层(即不住在 1 楼).
5. Fletcher(弗莱彻): 既不住在顶层(5 楼),也不住在底层(1 楼).
6. Miller(米勒): 住的楼层比 Cooper 更高(即 Miller 的楼层号 > Cooper 的楼层号).
7. Smith(史密斯): 住的楼层不挨着 Fletcher(即两人的楼层号之差的绝对值 ≠ 1).
8. Fletcher(弗莱彻): 住的楼层不挨着 Cooper(即两人的楼层号之差的绝对值 ≠ 1).

"What button do I press?", he asks.

# Exercise 1 - Brute force

Write a program that finds out where everyone lives.

To do this, think about a brute force solution. You know that Baker, Cooper, Fletcher, Miller, and Smith all live on different floors.
One way to solve it would be to cycle through all permutations of the floors and to enforce the various rules as a series of constraints. Print out the solutions that pass them all.

Is there only one solution? Multiple solutions? No solutions?

[exercise_01.py](./code/puzzle/exercise_01.py)

```python
def brute_force():
    import operator

    for baker in range(1, 6):
        for cooper in range(1, 6):
            for fletcher in range(1, 6):
                for miller in range(1, 6):
                    for smith in range(1, 6):
                        # All must live on different floors.
                        if len({baker, cooper, fletcher, miller, smith}) != 5:
                            continue
                        # ... more constraints ...
                        if baker == 5:
                            continue
                        if cooper == 1:
                            continue
                        if fletcher in {1, 5}:
                            continue
                        if miller < cooper:
                            continue
                        if abs(smith - fletcher) == 1:
                            continue
                        if abs(cooper - fletcher) == 1:
                            continue

                        results = [
                            (baker, "Baker"),
                            (cooper, "Cooper"),
                            (fletcher, "Fletcher"),
                            (miller, "Miller"),
                            (smith, "Smith"),
                        ]
                        # Print a solution
                        [
                            print(f"<{floor}>-{name}")
                            for floor, name in sorted(
                                results, key=operator.itemgetter(0), reverse=True
                            )
                        ]
```

输出：

```sh
<5>-Miller
<4>-Fletcher
<3>-Baker
<2>-Cooper
<1>-Smith
```

# Exercise 2 - Developing a better vocabulary

One of the problems in the brute force solution is that the code is messy. For example, there is the outer nesting of for-loops and there are the repeated clauses such as this:

```python
if baker == 5:
    continue
```

Maybe the code could be simplified if you **developed a better vocabulary for expressing the problem**. For example, maybe the above `block of if-continue combinations` could be rewritten as follows:

准备使用`Exception`来平替`continue`的方法

```python
require(distinct(baker, cooper, fletcher, miller, smith))
forbid(baker == 5)
forbid(cooper == 1)
forbid(fletcher == 1 or fletcher == 5)
require(miller > cooper)
forbid(adjacent(smith, fletcher))
forbid(adjacent(fletcher, cooper))
```

where `require()` and `forbid()` are some functions that you define. Similarly, perhaps complex expressions such as `len({baker, cooper, fletcher, miller, smith}) != 5` could be expressed in a more descriptive form such as:

```python
require(distinct(baker, cooper, fletcher, miller, smith))
```

Lastly, library functions such as `itertools.product()` might be able to replace the outer group of nested for-loops with something less verbose.

YOUR TASK: Create some helper functions such as `require()`, `forbid()`, `distinct()`, and `adjacent()` that help simplify the problem specification and rewrite the brute force solution to use these functions.

- problem specification /ˈprɑː.bləm ˌspes.ə.fɪˈkeɪ.ʃən/ n. 问题规格说明；问题定义（指对一个问题所做的完整、清晰、结构化的描述，包括有哪些变量、它们的取值范围、以及必须满足的约束条件。它只描述“问题是什么”，而不描述“怎么解决”）

Hint: `require()` and `forbid()` are supposed to abandon the current search and skip to the next iteration. However, there seems to be no obvious way to stick an isolated `continue` statement inside a function. However, you might be able to emulate the behavior of `continue` with an exception.

- abandon /əˈbæn.dən/ v. 放弃；抛弃；中止
- stick /stɪk/ v. 粘贴；放置；塞入


[exercise_02.py](./code/puzzle/exercise_02.py)

```python
class Fail(Exception):
    pass

def require(test):
    if not test:
        raise Fail()

def forbid(test):
    require(not test)

def distinct(*args):
    return len(args) == len(set(args))

def adjacent(x, y):
    return abs(x - y) == 1

def better_force():
    import itertools

    for baker, cooper, fletcher, miller, smith in itertools.product(
        range(1, 6), repeat=5
    ):
        try:
            require(distinct(baker, cooper, fletcher, miller, smith))
            require(baker != 5)
            require(cooper != 1)
            forbid(fletcher == 1 or fletcher == 5)
            require(miller > cooper)
            forbid(adjacent(smith, fletcher))
            forbid(adjacent(fletcher, cooper))
            print_apartment(baker, cooper, fletcher, miller, smith)
        except Fail:
            pass
```

---

# Exercise 3 - Separation of Details

In the above problem, there are high-level details that we actually care about. For example, the specification of problem variables and the logical constraints:

Variables:

```python
baker = {1, 2, 3, 4, 5}
cooper = {1, 2, 3, 4, 5}
fletcher = {1, 2, 3, 4, 5}
miller = {1, 2, 3, 4, 5}
smith = {1, 2, 3, 4, 5}
```

Constraints:
```python
require(distinct(baker, cooper, fletcher, miller, smith))
require(baker != 5)
require(cooper != 1)
forbid(fletcher == 1 or fletcher == 5)
require(miller > cooper)
forbid(adjacent(smith, fletcher))
forbid(adjacent(fletcher, cooper))
```

However, there are also low-level implementation details that we don't care about. For example, the outer for-loop driving the search, details related to control flow (e.g., exception handling), etc.

Is there any way to more elegantly separate these details from each other? That is, can you make it easy for someone to specify the problem at a high level without getting bogged down in implementation details of the search?

This is a somewhat open-ended problem, but as one possible idea, perhaps the code that generates the test candidates and could be moved into its own function. Perhaps it could generate possible solutions using a generator or iterator of some kind.


1. `apartment` 是规则本身（specification），
2. `domain` 是取值范围，
3. `find_solutions` 是搜索引擎。

Invariants (rules)
```python
def apartment(baker, cooper, fletcher, miller, smith):
    require(distinct(baker, cooper, fletcher, miller, smith))
    require(baker != 5)
    require(cooper != 1)
    forbid(fletcher == 1 or fletcher == 5)
    require(miller > cooper)
    forbid(adjacent(smith, fletcher))
    forbid(adjacent(fletcher, cooper))
```

Domain (possible values)
```python
domain = {
    'baker': range(1, 6),
    'cooper': range(1, 6),
    'fletcher': range(1, 6),
    'miller': range(1, 6),
    'smith': range(1, 6)
}
```
Find solutions
```python
solutions = find_solutions(apartment, domain)
for soln in solutions:
    print(soln)
```
Hints: You can iterate over all possible values of `domain` using `itertools.product()` like this:

```python
for values in itertools.product(*domain.values()):
    ... # values is a tuple
```

You can create a dictionary from values using
`dict(zip(domain.keys(), values))`


[exercise_03.py](./code/puzzle/exercise_03.py)

```python
def find_solutions(apartment, domain):
    for values in itertools.product(*domain.values()):
        try:
            candidates = dict(zip(domain.keys(), values))
            apartment(**candidates)
            # 利用生成器的方式很巧妙
            yield candidates
        except Fail:
            pass
```

# Exercise 4 - The Elevator Puzzle

While riding on the elevator, Graydon starts to think--"just how
hard could it be to code an elevator anyways?"

How many valid operational states does an elevator have? Let's try
to find out.

Suppose the elevator operates in 6 basic modes:

- `IDLE`: The elevator is just sitting there with the doors closed. There are no pending requests of any kind. It is not moving in any direction.

- `MOVINGUP`: Elevator is currently moving up.

- `MOVINGDOWN`: Elevator is currently moving down.

- `LOADINGUP`: Elevator is loading passengers and will continue to move up afterwards (shows an illuminated "up" arrow)

- `LOADINGDOWN`: Elevator is loading passengers and will continue to move down afterwards (shows an illuminated "down" arrow)

- `UNLOADING`: Elevator is unloading a passenger, but no future requests are pending. If no buttons get pressed, the elevator will return to IDLE when the doors close. (no arrow is illuminated).

In addition to a "mode", you need to know the following information:

1. The current floor (1-5)
2. Destinations (1-5). The *set* of buttons pressed inside the elevator car.
3. Up requests (1-4). The *set* of "up" buttons pressed in the hallway.
4. Down requests (2-5). The *set* of "down" buttons pressed in the hallway.

Your challenge: write a solver specification that precisely defines all *valid* elevator operational states. In doing this, there are a few general requirements:

1. The elevator should never move up on the top floor or move down on the bottom floor. This includes telling the passengers that it's going to continue going up when loading passengers on the top floor.

2. If the elevator is moving, there should be a reason for it to be moving. For example, if the mode is MOVINGUP, there must be some kind of pending request on a higher floor.

3. The elevator should never be IDLE if there are pending requests.

4. Others???? The above constraints are probably the "big" requirements. However, there could be a lot of minor-level features concerning buttons. For example, if the elevator is LOADINGUP on floor 3, the "up" button on floor 3 should be turned off (because the elevator is right there).

The button panels present a special challenge in this exercise because they capture the entire state of all buttons pressed.
For example, possible values for destinations could be:
set(), `{1}, {2}, {3}, {4}, {5}, {1, 2}, {1, 3}, ... {1, 2, 3, 4, 5}`.

To create this list of possible values, you might find the following function to be useful:

```python
def all_combinations(values):
    import itertools
    result = []
    for n in range(len(values)+1):
        result.extend(set(x) for x in itertools.combinations(values, n))
    return result

def elevator_spec(mode, floor, destinations, up_requests, down_requests):
    # Write logic rules here
    require(1 <= floor <= 5)
    ...

elevator_domain = {
    'mode': {'IDLE', 'MOVINGUP', 'MOVINGDOWN', 'LOADINGUP', 'LOADINGDOWN', 'UNLOADING'},
    'floor': range(1, 6),
    'destinations': all_combinations(range(1, 6)),
    'up_requests': all_combinations(range(1, 5)),
    'down_requests': all_combinations(range(2, 6))
}
```
How many solutions are there? Uncomment
```python
elevators = list(find_solutions(elevator_spec, elevator_domain))
print(len(elevators), "elevators")
```



#  Exercise 5 - The Elevator Testing Challenge

Graydon thoughts wandered to the frequently crashed elevator in his own building. "It's ridiculous," he thought, "that we computer people couldn't even make an elevator that works without crashing!" Thinking about this a bit longer, surely there must be a solution.

As luck would have it, Graydon found an open-source elevator package on GitHub. This code can be found in the file [elevator.py](./code/puzzle/elevator.py).

The code defines an `Elevator` class that can be instantiated for any given elevator state. For example:

```python
elev = Elevator(mode="MOVINGUP", destinations={4,5})
```

The elevator has a single public method that handles an input event of some kind. For example:

```python
elev.handle_event('destination', 2)    # "2" button pressed inside car
elev.handle_event('up_request', 3)     # "Up" button pressed on 3
elev.handle_event('down_request', 4)   # "Down" button pressed on 4
elev.handle_event('floor_sensor', 5)   # Arrived at floor 5
elev.handle_event('doors_close', 5)    # Doors close (boarding complete)
```

There are only 5 possible input events as shown. Each event takes place on a given floor. The "floor_sensor" event only occurs if the elevator is moving and only occurs for an adjacent floor (e.g., if the elevator is on floor 3 and moving up, then a ('floor_sensor', 4) event can occur). The "doors_close" event only occurs if the elevator is loading or unloading passengers on that floor.

Naturally, there are no unit tests or invariants in the code. However, the author is "pretty sure" that it works because just look at it.

Your task: Devise a "testing" strategy based on the idea of a solver and decide if you'd actually ride on this elevator.

To do this, you're going to combine a few ideas. First, in exercise 4, you wrote a logic specification function that defined "valid" elevator states.

Second, you wrote a `find_solutions()` function that can generate all possible solutions to a logic specification. Perhaps this can be used to create all valid `Elevator` instances.

Third, can you do anything to make a good elevator go bad? If so, it would probably involve events. So, maybe that suggests a possible testing strategy as outlined in the pseudocode below. Can you make this work?

```python
def test_elevator():
    from elevator import Elevator
    # Iterate over all good elevators
    for state in find_solutions(elevator_spec,elevator_domain): 
        # This is pseudo-code. You have to fill in details.
        # Try all possible events on the elevator that can occur in this state
        for event, floor in all_possible_events(state):
            # Create an elevator instance
            elev = Elevator(**state)
            # Try the event
            elev.handle_event(event, floor)
            # Verify that it's still a good elevator using the
            # spec. If this fails, then there's a bug in the elevator
            # software.
            try:
                elevator_spec(**vars(elev))
            except Fail:
                print(f"BAD! {state} : {{event},{floor}} -> {elev}")
```

Hint: Make sure you only generate 'floor_sensor' events if the elevator is moving and only for an adjacent floor (i.e., if MOVINGUP on floor 3, you can trigger the floor sensor for floor 4). Also, make sure you only generate 'doors_close' events if the elevator is loading or unloading passengers.

Hint: There is an even more clever implementation of this idea that uses `find_solutions()` twice.