import sys
from array import array

DATA_SIZE = 1_000_000

# list 占内存大
lst = list(range(DATA_SIZE))
arr = array("I", range(DATA_SIZE))

# 获取字节数
lst_size = sys.getsizeof(lst)
arr_size = sys.getsizeof(arr)

# 转换为 MB (1 MB = 1024 * 1024 = 1,048,576 字节)
lst_mb = lst_size / (1024 * 1024)
arr_mb = arr_size / (1024 * 1024)

print(f"List:  {lst_mb:.2f} MB ({lst_size:,} 字节)")
print(f"Array: {arr_mb:.2f} MB ({arr_size:,} 字节)")
