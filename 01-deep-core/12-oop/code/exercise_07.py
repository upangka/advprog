from decimal import Decimal


class Account:
    # 类型提示
    # 只有类型提示（没有默认值）
    owner: str  # ← 限制 self.owner 的类型
    _balance: Decimal  # ← 限制 self._balance 的类型

    # 类型提示 + 默认值
    nums_count: int = 0  # ← 这是真正的类变量！

    def __init__(self, owner, balance: float):
        self.owner = owner
        self._balance = balance  # error: float is not assignable to Decimal
        Account.nums_count = "xxx"  # error: Literal['xxx'] is not assignable to 'int'


Account(1, 2)
