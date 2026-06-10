from collections import Counter
from collections.abc import Hashable, Iterable
from typing import TypeVar

# 等价Java <T extends Hashable> T mode(List<T> data)
HashableT = TypeVar("HashableT", bound=Hashable)


def mode(data: Iterable[HashableT]) -> HashableT:
    pairs = Counter(data).most_common(1)
    if not pairs:
        raise ValueError("no mode for empty data")
    return pairs[0][0]


# tuple is hashable
# As long as the inferred type is consistent with the boundary
a = mode([i for i in range(10)])

# ❌ list 是不可哈希的，类型检查器会报错
# b = mode([[1, 2], [3, 4], [1, 2]])
