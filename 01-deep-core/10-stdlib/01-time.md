
# 统计程序运行的时长

`time.time()`与`time.perf_counter()`都是以`S`秒为单位


[exercise_01.py](./code/timelib/exercise_01.py)
```python
start = time.time()
time.sleep(3.5)
end = time.time()
print(f"{end - start = :.1f}") # end - start = 3.5
```
“perf” = performance = 性能

[exercise_02.py](./code/timelib/exercise_02.py)
```python
start = time.perf_counter()
time.sleep(3.5)
end = time.perf_counter()
print(f"{end - start = :.1f}")  # end - start = 3.5
```