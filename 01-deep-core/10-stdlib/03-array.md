
# [array — Efficient arrays of numeric values](https://docs.python.org/3/library/array.html#module-array)

`array`的"memory-efficient"优势正是来自于它的`homogeneous/ˌhoʊ.məˈdʒiː.ni.əs/	同质`特性——因为所有元素类型相同，所以可以紧密排列存储，省去了每个元素的对象头（object header）和指针开销


大量数值存储，内存敏感

[exercise_01.py](./code/arraylib/exercise_01.py)

```python
"""list 占内存大
List:  7.63 MB (8,000,056 字节)
Array: 3.90 MB (4,091,948 字节)
"""
import sys
from array import array

DATA_SIZE = 1_000_000

lst = list(range(DATA_SIZE))
arr = array('I',range(DATA_SIZE))

# 获取字节数
lst_size = sys.getsizeof(lst)
arr_size = sys.getsizeof(arr)

# 转换为 MB (1 MB = 1024 * 1024 = 1,048,576 字节)
lst_mb = lst_size / (1024 * 1024)
arr_mb = arr_size / (1024 * 1024)

print(f"List:  {lst_mb:.2f} MB ({lst_size:,} 字节)")
print(f"Array: {arr_mb:.2f} MB ({arr_size:,} 字节)")
```



## 类型码

创建数组，必须指定类型码

```sh
>>> from array import array
>>> # 有符号整数
>>> array('i',[-1,2,-3,4,-5,6])
array('i', [-1, 2, -3, 4, -5, 6])
>>> # 无符号整数 0 整数
>>> array('I',[0,1,2,3,4,5,6])
array('I', [0, 1, 2, 3, 4, 5, 6])
>>> # 我们添加一个负数在无符号整数中，看看什么情况
>>> array('I',[0,1,2,3,4,5,-6])
Traceback (most recent call last):
  File "<python-input-27>", line 1, in <module>
    array('I',[0,1,2,3,4,5,-6])
    ~~~~~^^^^^^^^^^^^^^^^^^^^^^
OverflowError: can't convert negative value to unsigned int
>>> # 直接报错，哈哈哈
>>> # 获取typecode
>>> array('i',[]).typecode
'i'
```

查看支持的全部类型码 [python官网：array](https://docs.python.org/3/library/array.html#module-array)

```sh
>>> import array
>>> array.typecodes
'bBuwhHiIlLqQfd'
>>> len(array.typecodes)
14
```
