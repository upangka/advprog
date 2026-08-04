# The Mystery of the Walrus

## A)

Consider the code below. Next to each blank, write down the expected output. Alternatively, if it’s
impossible to predict the output, write “unknown”.

Implementations for `obliterate`, `IntSquasher`, `shamble`, and `agglutinate` are unknown.

```java
public class Walrus {
    public static void main(String[] args) {
        int x = 10;
        obliterate(x);
        System.out.println(x);  // 10

        int y = 20;
        IntSquasher isq = new IntSquasher(y);
        System.out.println(y);      // 20

        int[] z = new int[]{1, 2, 3};
        shamble(z[0]);
        System.out.println(z[0]);   // unknown

        agglutinate(z);
        System.out.println(z[1]);   // unknown
    }
}
```

| 行                          | 你的答案 | 正确性 | 原因                                                                                  |
| --------------------------- | -------- | ------ | ------------------------------------------------------------------------------------- |
| `System.out.println(x);`    | 10       | ✅     | `int` 按值传递，`obliterate` 改的是副本                                               |
| `System.out.println(y);`    | 20       | ✅     | `int` 按值传递，构造器改的是副本                                                      |
| `System.out.println(z[0]);` | unknown  | ✅     | `z[0]` 是 `int`，按值传递；但 `shamble` 的实现未知，无法预测                          |
| `System.out.println(z[1]);` | unknown  | ✅     | `z` 是数组引用，按值传递的是引用地址；但 `agglutinate` 是否修改 `z[1]` 未知，无法预测 |

## B)

Consider the class `MyInteger` below.

```java
public class MyInteger {
    public int val;
    public MyInteger(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return String.valueOf(this.val);
    }
}
```

If `z` was instantiated as

```java
MyInteger[] z = new MyInteger[]{new MyInteger(1), new MyInteger(2), new MyInteger(3)};
```

Would any of your answers change? If so, which ones, and why? If not, why not?

```java
public class Walrus {
    public static void main(String[] args) {
        int x = 10;
        obliterate(x);
        System.out.println(x);  // 10

        int y = 20;
        IntSquasher isq = new IntSquasher(y);
        System.out.println(y);      // 20

        MyInteger[] z = new MyInteger[]{new MyInteger(1), new MyInteger(2), new MyInteger(3)};

        shamble(z[0]);
        System.out.println(z[0]);   // unknown

        agglutinate(z);
        System.out.println(z[1]);   // unknown
    }
}
```

## C)

Implementations for `invertify`, `scrub`, and `feed` are unknown.

> `v = -10;` 这一行——没有 `this.` 前缀的赋值，操作的是方法参数，不是实例变量。

```java
public class WalrusReview {
    public int v;
    public static String name;

    public WalrusReview(int v) {
        this.v = v;
        name = "Scott";
        v = -10;
    }

    public static void main(String[] args) {
        int z = 10;
        WalrusReview wr = new WalrusReview(z);
        System.out.println(z); // 10
        System.out.println(wr.v);   // 10

        invertify(wr.v);
        System.out.println(wr.v); // 10

        scrub(WalrusReview.name);
        System.out.println(WalrusReview.name); // Scott

        z = 10;
        wr = new WalrusReview(z);
        feed(wr);
        System.out.println(z); // 10
        System.out.println(wr.v);   // unknown
        System.out.println(WalrusReview.name);  // unknown
    }
}
```

# Ranking Players

Fill in `rankedAbove`, which takes in a list of `Players` and returns a map from each `Player` to their rank. The player with the highest score has rank 1, the player with the next-highest score has rank 2, and so on. Assume no two players have the same score. For example, if we have a list of players with `scores` of 500, 800, 1200, and 100, then these players would have ranks 3, 2, 1, and 4, respectively, and `rankedAbove` would return the following `Map`:

```java
{
Player with score 500 : 3, Player with score 800 : 2,
Player with score 1200 : 1, Player with score 100 : 4
}
```

`Syntax hints` (you may not need all of these):

- A `Set` has the operations `add` and `contains`. You can instantiate one using `new HashSet`.
- A `map` has the operations `put`, `containsKey`, and `get`. You can instantiate one using `new HashMap`.
- A `list` has the operations `get` and `set`. You can instantiate one using `new ArrayList`.
- You can iterate over a `List<Integer>` or a `Set<Integer>` using `for int x : c`.
- `someMap.keySet()` will return the `Set` of all keys in the map `someMap`.

[rank_player.java](./code/worksheet/rank_player.java)

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class Player {
	public double score;

	public Player(double score) {
		this.score = score;
	}

	public static Map<Player, Integer> rankAbove(List<Player> players) {
		var results = new HashMap<Player, Integer>();
		for (Player player : players) {
			int rank = players.size();
			for (Player p : players) {
				if (p != player && player.score > p.score) {
					rank -= 1;
				}
			}
			results.put(player, rank);
		}

		return results;
	}
}

void main(String... args) {
	Player p1 = new Player(500);
	Player p2 = new Player(800);
	Player p3 = new Player(1200);
	Player p4 = new Player(100);
	List<Player> players = List.of(p1, p2, p3, p4);

	Map<Player, Integer> ret = Player.rankAbove(players);
	for (Map.Entry<Player, Integer> entry : ret.entrySet()) {
		System.out.println("Player with score %.2f : %d".formatted(
				entry.getKey().score, entry.getValue()));
	}
}
```

---

上面的版本算法复杂度是`O(n²)`，优化版本[rank_player_refactor.java](./code/worksheet/rank_player_refactor.java)`O(n log n)`。排序一次，然后一次遍历分配排名。

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class Player {
	public double score;

	public Player(double score) {
		this.score = score;
	}

	public static Map<Player, Integer> rankAbove(List<Player> players) {
		var results = new HashMap<Player, Integer>();

		// 复制一份用来排序
		var sortedPlayers = new ArrayList<>(players);
		sortedPlayers.sort((p1, p2) -> Double.compare(p2.score, p1.score));

		for (int i = 0; i < sortedPlayers.size(); i++) {
			results.put(sortedPlayers.get(i), i + 1);
		}
		return results;
	}
}

void main(String... args) {
	Player p1 = new Player(500);
	Player p2 = new Player(800);
	Player p3 = new Player(1200);
	Player p4 = new Player(100);
	List<Player> players = List.of(p1, p2, p3, p4);

	Map<Player, Integer> ret = Player.rankAbove(players);
	for (Map.Entry<Player, Integer> entry : ret.entrySet()) {
		System.out.println("Player with score %.2f : %d".formatted(
				entry.getKey().score, entry.getValue()));
	}
}
```

输出:

```txt
Player with score 100.00 : 4
Player with score 500.00 : 3
Player with score 1200.00 : 1
Player with score 800.00 : 2
```
