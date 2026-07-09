"""
采用的是面向对象的方式来建模，核心是把“计分规则”封装成一个独立的引擎，而不是把所有逻辑堆在循环里。

整体设计分为两块：

1. `HighCardEngine`：负责维护当前是否有“待计分的高牌”，以及还需要检查多少张普通牌。每输入一张牌，引擎判断它是高牌还是普通牌，并更新内部状态；只有连续遇到足够数量的普通牌时，引擎才会返回得分和对应的玩家。
2. `Player`：只负责记录玩家名字和累计分数，不参与计分逻辑。

这样做的好处是：

1. 计分规则和游戏流程解耦，主循环只负责“翻牌 + 切换玩家”；
2. 引擎内部状态清晰，便于处理“高牌被打断”的情况；
3. 如果以后规则变化，只需要修改引擎内部逻辑，不影响主流程。

关键处理点在于：当一张高牌出现时，不是立即计分，而是进入“待检查”状态，记录下当前玩家和需要检查的普通牌数量；后续每来一张普通牌就减少计数，只有计数归零时才真正触发得分。如果中途又出现高牌，则直接覆盖之前的待检查状态。

这种设计让我觉得代码更容易理解和扩展，也方便单独测试计分逻辑
"""

from __future__ import annotations

from typing import Optional

NUMS_CARD = 52
HIGH_CARDS = dict(jack=1, queen=2, king=3, ace=4)


def is_high_card(card):
    return card in HIGH_CARDS.keys()


class HighCardEngine:
    def __init__(self):
        """
        Args:
            target: 目标牌普通牌数/分数
            remaining: 需要检查多少张牌
            scoring_player: 得分归属
        """
        self.target: int = 0
        self.remaining: int = 0
        self.scoring_player: Optional[Player] = None

    def feed(self, card: str, player) -> Optional[tuple[int, Player]]:
        """
        喂一张牌给引擎。
        返回 (得分, 玩家) 如果触发了得分，否则返回 None。

        Args
            card: 牌
            player: 此时的玩家
        Return
            (score 得分，player) | 无得分直接返回 0
        """
        if is_high_card(card):
            self._update_status(card, player)
            return None
        elif self.remaining > 0:
            self.remaining -= 1
            if self.remaining == 0:
                result = self.target, self.scoring_player
                self.__init__()
                return result
        return None

    def _update_status(self, card, player):
        self.target = HIGH_CARDS[card]
        self.remaining = HIGH_CARDS[card]
        self.scoring_player = player


class Player:
    def __init__(self, name):
        self.name = name
        self.score = 0


def main():
    engine = HighCardEngine()
    player_a = Player("A")
    player_b = Player("B")
    turn_a = True

    for _ in range(NUMS_CARD):
        card = input()
        player = player_a if turn_a else player_b
        if result := engine.feed(card, player):
            score, player = result
            print(f"Player {player.name} scores {score} point(s).")
            player.score += score
        turn_a = not turn_a

    print(f"Player A: {player_a.score} point(s).")
    print(f"Player B: {player_b.score} point(s).")


if __name__ == "__main__":
    main()
