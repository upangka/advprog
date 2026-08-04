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

		// 复制一份列表用于排序，避免修改原列表（non-destructive）
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

---

# Static Books

Suppose we have the following `Book` and `Library` classes

[static_books.java](./code/worksheet/static_books.java)

```java
class Book {
    public String title;
    public Library library;
    public static Book last = null;

    public Book(String name) {
        title = name;
        last = this;
    }

    public static String lastBookTitle() {
        return last.title;
    }

    public String getTitle() {
        return title;
    }
}

class Library {
    public Book[] books;
    public int index;
    public static int totalBooks = 0;

    public Library(int size) {
        books = new Book[size];
        index = 0;
    }

    public void addBook(Book book) {
        books[index] = book;
        index++;
        totalBooks++;
        book.library = this;
    }
}
```

## A)

For each modification below, determine whether the code of the `Library` and `Book` classes will compile
or error if we only made that modification, i.e. treat each modification independently.

1. Change the `totalBooks` variable to `non static`
   - `compile`

2. Change the `lastBookTitle` method to `non static`
   - `compile`

3. Change the `addBook` method to `static`
   - `error`

4. Change the `last` variable to `non static`
   - `error`

5. Change the `library` variable to `static`
   - `compile`

---

# B)

Using the original `Book` and `Library` classes (i.e., without the modifications from part `A)`, write the output
of the main method below. If a line errors, put the precise reason it errors and continue execution.

[static_books_b.java](./code/worksheet/static_books_b.java)

```java
void main(String[] args) {
        System.out.println(Library.totalBooks);  // 0

        System.out.println(Book.lastBookTitle()); // error 因为last为null

        // System.out.println(Book.getTitle());    // error 因为getTitle是实例方法

        Book goneGirl = new Book("Gone Girl");
        Book fightClub = new Book("Fight Club");

        System.out.println(goneGirl.title);     // Gone Girl

        System.out.println(Book.lastBookTitle());  // Fight Club

        System.out.println(fightClub.lastBookTitle()); // Fight Club

        System.out.println(goneGirl.last.title); // Fight Club

        Library libraryA = new Library(1);
        Library libraryB = new Library(2);
        libraryA.addBook(goneGirl);

        System.out.println(libraryA.index); // 1

        System.out.println(libraryA.totalBooks); // 1

        libraryA.totalBooks = 0;
        libraryB.addBook(fightClub);
        libraryB.addBook(goneGirl);

        System.out.println(libraryB.index);    // 2

        System.out.println(Library.totalBooks); // 2

        System.out.println(goneGirl.library.books[0].title); // Gone Girl
    }
```

---

# Country Club

Avik wants to keep track of the students in UC Berkeley's clubs. Each club is represented by the **Club** class below, which maps every student in that club to their home country.

```java
public class Club {
    public Map<Student, Country> countryMap;
    ...
}

public class Student { ... }
public class Country { ... }
```

On the next page, implement **countByCountry**, which takes in a list of **Clubs**, and returns a map from each **Country** to the number of unique students from that country. The map should only contain countries that appear in the **countryMaps**.

If a **Student** is in multiple clubs, then each of those clubs will map that student to the same **Country**. Make sure to avoid counting the same **Student** twice if they are in multiple clubs.

You may assume that there is at least one club, and each club has at least one student.

Here is an example with 2 clubs and 3 total students:

| Club          | Country Map                                               |
| :------------ | :-------------------------------------------------------- |
| Chess Club    | `{ Aditya: Scotland, Natalia: Brazil, Rushil: Scotland }` |
| Climbing Club | `{ Natalia: Brazil }`                                     |

**countByCountry** should return the following map: `{ Brazil: 1, Scotland: 2 }`.

---

**Code Skeleton**

```java
public static Map<Country, Integer> countByCountry(List<Club> allClubs) {
    Map<Country, Integer> counts = ______;
    ______;
    for (______) {
        for (Student s : ______.keySet()) {
            Country c = ______;
            ______;
        }
    }
    return counts;
}
```

**Syntax Hints**

- A `Set` has the operations `add` and `contains`. You can instantiate one using `new HashSet`.
- A `Map` has the operations `put`, `containsKey`, and `get`. You can instantiate one using `new HashMap`.
- A `List` has the operations `get` and `set`. You can instantiate one using `new ArrayList`.
- You can iterate over a `List<Integer>` or a `Set<Integer>` using `for int x : c`.
- `someMap.keySet()` returns the `Set` of all keys in the map `someMap`.

[country_club.java](./code/worksheet/country_club.java)

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS tools.jackson.core:jackson-databind:3.2.1

import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

static class Club {
	private Map<Student, Country> countryMap;

	public Club() {
		this.countryMap = new HashMap<>();
	}

	public void addStudent(Student stu, Country country) {
		this.countryMap.put(stu, country);
	}
}

static record Country(String name) {

	@Override
	public final String toString() {
		return this.name;
	}
}

static record Student(String name) {
}

static Map<Country, Integer> countByCountry(List<Club> allClubs) {
	var counts = new HashMap<Country, Integer>();
	var uniqueStudents = new HashSet<Student>();
	for (Club club : allClubs) {
		for (Map.Entry<Student, Country> entry : club.countryMap.entrySet()) {

			Student stu = entry.getKey();
			Country country = entry.getValue();

			if (!uniqueStudents.contains(stu)) {
				int count = counts.computeIfAbsent(country, k -> 0);
				counts.put(country, count + 1);
				uniqueStudents.add(stu);
			}
		}
	}
	return counts;
}

static List<Club> createClubs() {
	// 创建国家
	Country scotland = new Country("Scotland");
	Country brazil = new Country("Brazil");

	// 创建学生
	Student aditya = new Student("Aditya");
	Student natalia = new Student("Natalia");
	Student rushil = new Student("Rushil");

	// 俱乐部1：国际象棋俱乐部
	Club chessClub = new Club();
	chessClub.addStudent(aditya, scotland);
	chessClub.addStudent(natalia, brazil);
	chessClub.addStudent(rushil, scotland);

	// 俱乐部2：攀岩俱乐部
	Club climbingClub = new Club();
	climbingClub.addStudent(natalia, brazil);

	return List.of(chessClub, climbingClub);
}

void main(String... args) {
	List<Club> clubs = createClubs();
	var ret = countByCountry(clubs);

	JsonMapper jsonMapper = JsonMapper.builder()
		.enable(SerializationFeature.INDENT_OUTPUT)
		.build();

	System.out.println(jsonMapper.writeValueAsString(ret));
}

/**output
{
  "Brazil" : 1,
  "Scotland" : 2
}
*/
```
