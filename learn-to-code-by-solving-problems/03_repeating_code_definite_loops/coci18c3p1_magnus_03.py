class HoniStateMachine:
    WORD = "HONI"

    def __init__(self):
        # self.stages = [
        #    (stage, target) for stage, target in zip(range(len(self.WORD)), self.WORD)
        # ]
        self.stages = list(enumerate(self.WORD))
        self._handled = 0
        self.current_stage = self.stages[0][0]

    def change_stage(self, c: str):
        if c == self._find_need_target():
            self.current_stage += 1
        if self.current_stage >= len(self.stages):
            self.current_stage = self.stages[0][0]
            self._handled += 1

    def _find_need_target(self) -> str:
        for stage, target in self.stages:
            if stage == self.current_stage:
                return target
        raise RuntimeError("Inner Error")

    @property
    def handled(self):
        return self._handled


machine = HoniStateMachine()

for c in input():
    machine.change_stage(c)

print(machine.handled)
