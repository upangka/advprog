
The owner of a monopolistic movie theater in a small town has
complete freedom in setting ticket prices. The more he charges, the
fewer people can afford tickets. The less he charges, the more it
costs to run a show because attendance goes up. In a recent
experiment the owner determined a relationship between the price of
a ticket and average attendance.

- theater /ˈθiː.ə.t̬ər/ n. 剧院；电影院（在上下文中，指电影院，即放映电影供观众观看的场所）
- monopolistic /məˌnɑː.pəˈlɪs.tɪk/ adj. 垄断的；独占的（指市场上只有一个卖家，没有竞争对手，因此可以自由定价，不受市场竞争约束。
- afford /əˈfɔːrd/ v. 买得起；负担得起(指有足够的钱购买某物或支付某项费用)

At a price of $5.00/ticket, 120 people attend a performance. For
each 10-cent change in the ticket price, the average attendance
changes by 15 people. That is, if the owner charges $5.10, some 105
people attend on the average; if the price goes down to $4.90,
average attendance increases to 135.

Unfortunately, the increased attendance also comes at an increased
cost. Every performance comes at a fixed cost of $180 to the owner
plus a variable cost of $0.04 per attendee.

- performance /pərˈfɔːr.məns/ n. 演出；表演；放映(指电影院的一场放映活动)

The problem has a variety of parameters related to the calculation.
It's generally a bad idea to just hard-code these directly. Better
to assign to variables with names.

```python
BASE_PRICE = 5.0                # Dollars
ATTENDEES_PER_DOLLAR = 150      # 150 people per dollar (15 people per 0.10 dollar)
BASE_ATTENDEES = 120            # Number of attendees at base price
FIX_COST = 180.0                # Dollars
COST_PER_ATTENDEE = 0.04        # Dollars

def compute_attendees(price):
    ...

def compute_cost(attendees):
    ...

def compute_profit(price):
    ...
```




The owner would like to know the exact relationship between profit
and ticket price in order to maximize the profit.

Write a program to figure out the best ticket price (to the nearest
10 cents) that maximizes profit.

Credit: This problem comes from "How to Design Programs", 2nd Ed.

Credit /ˈkred.ɪt/ n. 荣誉；致谢；来源（指对某作品、想法或问题的原作者/来源的承认和感谢）


- I want to approach this as a software problem, not an algebra problem. Yes, you could solve this specific problem using algebra, but programs are a bit different. For example, programs can be expanded with new features. Maybe you want to be able to customize the problem. Maybe your code is to be incorporated into a larger program of some kind.

- The code should be broken into reasonable parts that can be tested and/or debugged.

- Readability counts. Could I give this code to others and have them understand it? Could they modify it?

[theater](./code/theater/theater.py)