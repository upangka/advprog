相比搜集所有结果或者解决方案才返回，不如得到一个解决方法或者结果就直接返回

```python
def find_solutions(apartment, domain):
    solutions = []
    for values in itertools.product(*domain.values()):
        try:
            candidates = dict(zip(domain.keys(), values))
            apartment(**candidates)
            # 先收集结果
            solutions.append(candidates) 
        except Fail:
            pass
    # 返回所有得到的结果
    return solutions


def main():
    solutions = find_solutions(apartment, domain)
    # 消费已经计算得到的结果
    for soln in solutions:
        print_apartment(**soln)
```

---

**生成器: 得到一个解决方法或者结果就直接返回**

```python
def find_solutions(apartment, domain):
    for values in itertools.product(*domain.values()):
        try:
            candidates = dict(zip(domain.keys(), values))
            apartment(**candidates)
            # 直接返回结果
            yield candidates
        except Fail:
            pass


def main():
    solutions = find_solutions(apartment, domain)
    # 得到一个结果，消费一个结果
    for soln in solutions:
        print_apartment(**soln)
```