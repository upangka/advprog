
# with obj

`with obj` 语句允许对象`obj`管理控制流进入和退出与之相关的语句时发生的事情。从钩子的角度来说就是有`__enter__`和`__exit__`这两个钩子函数可以调用。

功能上下面两种写法，完全一样（`__enter__`和`__exit__`的执行时机相同）

[exercise_01.py](./code/context/exercise_01.py)

```python
class Manager:
    def __init__(self,x):
        self.x = x

    def __enter__(self):
        print("enter...")
        # 返回一个列表
        return [1,3,5]

    def __exit__(self,ty,val,tb):
        """
        type,value,traceback
        """
        print("exit...")

# m = Manager(3)
# with m as l:
with Manager(3) as l:
    for i in l:
        print(i)

print("Over...")
```


## 案例: 列表事务

1. `__enter__`暴露要操作的数据对象
2. `__exit__`决定要不要将`with`块中操作的变更的数据更新到原数据中

[exercise_02.py](./code/context/exercise_02.py)
```python
class ListTransaction:
    def __init__(self, orig_list):
        self.orig_list = orig_list

    def __enter__(self):
        self.workingcopy = list(self.orig_list)
        return self.workingcopy

    def __exit__(self, ty, val, tb):
        if ty is None:
            self.orig_list[:] = self.workingcopy
        print(f"{ty=}, {val=}, {tb=}")
        return False
```

```python
def main():
    import random

    orig_list = [random.randint(1, 10) for _ in range(10)]
    print(f"{orig_list=}")
    try:
        with ListTransaction(orig_list) as working:
            working.append(random.randint(20, 30))
            working.append(random.randint(20, 30))
            raise RuntimeError("Oops Something bad happened.")
    except Exception as err:
        pass
    print(f"{orig_list=}")
    print("-----------------------------------------------------")
    with ListTransaction(orig_list) as working:
        working.append(random.randint(20, 30))
        working.append(random.randint(20, 30))
    print(f"{orig_list=}")
```



```sh
orig_list=[4, 3, 10, 4, 2, 5, 9, 6, 9, 9]
ty=<class 'RuntimeError'>, val=RuntimeError('Oops Something bad happened.'), tb=<traceback object at 0x7f3696ab3800>
orig_list=[4, 3, 10, 4, 2, 5, 9, 6, 9, 9]
-----------------------------------------------------
ty=None, val=None, tb=None
orig_list=[4, 3, 10, 4, 2, 5, 9, 6, 9, 9, 23, 26]
```