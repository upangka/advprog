import random


class Account:
    def __init__(self, owner, balance=0):
        self.owner = owner
        self.balance = balance
        self.transactions = []

    def deposit(self, amount):  # 修改状态
        self.balance += amount
        self.transactions.append(f"Deposit: +{amount}")

    def withdraw(self, amount):  # 修改状态
        if amount > self.balance:
            raise ValueError("Insufficient funds")
        self.balance -= amount
        self.transactions.append(f"Withdraw: -{amount}")

    def inquiry(self):  # 只读，不修改状态
        """查询当前余额"""
        return self.balance

    def statement(self):  # 只读，不修改状态
        """查询交易记录"""
        return self.transactions.copy()


class EvilAccount(Account):

    def inquire(self):
        if random.randint(0, 4) == 1:
            return self.balance * 1.10
        else:
            # return super().balance
            return super().inquiry()
