"""
┌─────────────┬──────┬──────────────┬──────┐
│ 汉堡        │ 热量 │ 配菜         │ 热量 │
├─────────────┼──────┼──────────────┼──────┤
│ 1-芝士汉堡  │ 461  │ 1-薯条       │ 100  │
│ 2-鱼肉汉堡  │ 431  │ 2-烤土豆     │ 57   │
│ 3-蔬菜汉堡  │ 420  │ 3-主厨沙拉   │ 70   │
│ 4-不要汉堡  │ 0    │ 4-不要配菜   │ 0    │
├─────────────┼──────┼──────────────┼──────┤
│ 饮料        │ 热量 │ 甜点         │ 热量 │
├─────────────┼──────┼──────────────┼──────┤
│ 1-软饮料    │ 130  │ 1-苹果派     │ 167  │
│ 2-橙汁      │ 160  │ 2-圣代       │ 266  │
│ 3-牛奶      │ 118  │ 3-水果杯     │ 75   │
│ 4-不要饮料  │ 0    │ 4-不要甜点   │ 0    │
└─────────────┴──────┴──────────────┴──────┘

Run:
    uv run python ccc06j1_calorie.py --log=debug
"""

import argparse
import logging

parser = argparse.ArgumentParser()
parser.add_argument(
    "--log", default="WARNING", help="日志级别: DEBUG, INFO, WARNING, ERROR, CRITICAL"
)
args = parser.parse_args()
loglevel = getattr(logging, args.log.upper())
logging.basicConfig(level=loglevel, encoding="utf-8")
logger = logging.getLogger(__name__)

# 定义菜单
menus = [
    # 0: 汉堡
    {
        1: ("Cheeseburger", 461),
        2: ("Fish Burger", 431),
        3: ("Veggie Burger", 420),
        4: ("no burger", 0),
    },
    # 1: 配菜
    {
        1: ("Fries", 100),
        2: ("Baked Potato", 57),
        3: ("Chef Salad", 70),
        4: ("no side order", 0),
    },
    # 2: 饮料
    {
        1: ("Soft Drink", 130),
        2: ("Orange Juice", 160),
        3: ("Milk", 118),
        4: ("no drink", 0),
    },
    # 3: 甜点
    {
        1: ("Apple Pie", 167),
        2: ("Sundae", 266),
        3: ("Fruit Cup", 75),
        4: ("no dessert", 0),
    },
]

# 读取4个选择：汉堡 → 配菜 → 饮料 → 甜点
choices = [int(input()) for _ in range(4)]

# 计算总热量
total = 0
for choice, menu in zip(choices, menus):
    if logger.isEnabledFor(logging.DEBUG):
        logger.debug(f"{choice} => {menu[choice]}")
    total += menu[choice][1]

print(f"Your total Calorie count is {total}.")

"""Running
$ uv run python ccc06j1_calorie.py --log=debug
2
1
3
4
DEBUG:__main__:2 => ('Fish Burger', 431)
DEBUG:__main__:1 => ('Fries', 100)
DEBUG:__main__:3 => ('Milk', 118)
DEBUG:__main__:4 => ('no dessert', 0)
Your total Calorie count is 649.
"""
