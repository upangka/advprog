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

    def handle_card(self, card: str, player) -> Optional[tuple[int, Player]]:
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
            return
        elif self.remaining > 0:
            self.remaining -= 1
            if self.remaining == 0:
                result = self.target, self.scoring_player
                self.__init__()
                return result
        return

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
        if result := engine.handle_card(card, player):
            score, player = result
            print(f"Player {player.name} scores {score} point(s).")
            player.score += score
        turn_a = not turn_a

    print(f"Player A: {player_a.score} point(s).")
    print(f"Player B: {player_b.score} point(s).")


if __name__ == "__main__":
    main()
