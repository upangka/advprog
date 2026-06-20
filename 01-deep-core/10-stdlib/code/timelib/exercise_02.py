"""
验证以s为计算单位
"""

import time

start = time.perf_counter()
time.sleep(3.5)
end = time.perf_counter()
print(f"{end - start = :.1f}")  # end - start = 3.5
