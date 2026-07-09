class SlotMachine:
    def __init__(self, threshold, payout, played):
        """
        Args:
            threshold: 中奖所需的游玩次数（达到该次数时触发奖励）
            payout: 中奖时奖励的硬币数量
            played: 当前机器自上次中奖以来已被玩过的次数
        """
        self._threshold = threshold
        self._payout = payout
        self._played = played

    def run(self) -> int:
        self._played += 1
        if self._played % self._threshold == 0:
            return self._payout
        return 0


class Player:
    def __init__(self, coins=0):
        self._coins = coins
        self._played = 0
        self._machines = []

    def set_machines(self, machines=[]):
        self._machines = machines
        self.set_current_play(0)

    def set_current_play(self, idx):
        if idx < 0 or idx >= len(self._machines):
            raise RuntimeError("No available slot machines for " + idx)
        self._current_play = idx

    def play(self):
        while self:
            self._coins -= 1
            self._played += 1
            payout = self.current_machine.run()
            self._coins += payout
            self.next_machine()

        return self._played

    @property
    def current_machine(self):
        return self._machines[self._current_play]

    @property
    def played(self):
        return self._played

    def __bool__(self):
        return self._coins > 0

    def next_machine(self):
        self._current_play += 1
        if self._current_play >= len(self._machines):
            self._current_play = 0

    def set_coins(self, coins):
        self._coins = coins


MACHINE_CONFIG = [(35, 30), (100, 60), (10, 9)]


def main():
    import functools

    martha = Player()
    machines_callers = [
        functools.partial(SlotMachine, threshold, payout)
        for threshold, payout in MACHINE_CONFIG
    ]

    coins, *machine_playeds = [int(input()) for _ in range(4)]

    machines = []

    for i in range(3):
        caller = machines_callers[i]
        m = caller(machine_playeds[i])
        machines.append(m)

    martha.set_coins(coins)
    martha.set_machines(machines)
    martha.play()
    print(f"Martha plays {martha.played} times before going broke.")


if __name__ == "__main__":
    main()
