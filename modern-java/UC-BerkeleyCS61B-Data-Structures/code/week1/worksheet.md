In 61B, we'll be learning the Java programming language, with an **emphasis** on the usage and creation of data structures. Let's kick off the class by reviewing how lists and maps (called dictionaries in Python) work. Below, write the requested Python functions. Use loops. **Do not use any list or dictionary comprehensions (we won't learn them in 61B).**

- Emphasis /ˈem.fə.sɪs/ “重点（放在...上）” 或 “侧重点”

在 61B 中，我们将学习 Java 编程语言，**重点**放在数据结构的**使用**和**创建**上。让我们从复习列表（list）和映射（map，在 Python 中叫 dictionary）如何工作开始。请在下方编写要求的 Python 函数。**使用循环（loops）**。**不要使用任何列表推导式或字典推导式（因为 61B 不教这些）。**

[main.py](./worksheet/main.py)

```python
def evens(list_of_ints):
    """Returns a copy of the list but keeping only the even numbers."""
    ret = []

    for idx in range(len(list_of_ints)):
        if (list_of_ints[idx] & 1) == 0:
            ret.append(list_of_ints[idx])
    return ret

def count_words(list_of_words):
    """Returns a map from each word to its count."""
    counts = {}
    for word in list_of_words:
        counts[word] = counts.get(word,0) + 1
    return counts

nums = [2, 5, 8, 7, 3, 10]
words = ["hello", "world", "hello", "cs61b", "world", "hello"]

expected_evens = [2, 8, 10]
assert evens(nums) == expected_evens, f"evens 测试失败: 期望 {expected_evens}, 实际得到 {evens(nums)}"

expected_words = {'hello': 3, 'world': 2, 'cs61b': 1}
assert count_words(words) == expected_words, f"count_words 测试失败: 期望 {expected_words}, 实际得到 {count_words(words)}"

print("所有测试通过 ✅")
```

Below, we see the Java solution to these problems. Discuss with your group what interesting features you observe in the Java code. If you have any Java veterans in your group, grill them about the weirdness, or of course feel free to ask your TA.

- veteran /ˈvet̬.ər.ən/：老手、资深者。在 CS 61B 里指组里已经学过 Java、对语法比较熟悉的同学。
- grill /ɡrɪl/：盘问、追问。在这里不是“烤”的意思，而是鼓励你打破砂锅问到底，不懂就问组里懂 Java 的人，别客气。
- weirdness /ˈwɪrd.nəs/：奇怪之处、反直觉的地方。指 Java 代码里那些你看不懂、和 Python 不一样的语法特征。
- TA /ˌtiːˈeɪ/：助教（Teaching Assistant 的缩写）。负责带 discussion、改作业、在论坛上答疑的人。

下面我们来看这些问题的 Java 解法。和你的小组成员讨论一下，你在 Java 代码中观察到了哪些有趣的特征。如果你组里有 Java 老手，尽管追问他们这些"奇怪"的地方，当然也可以随时问你的 TA。

[main.java](./worksheet/main.java)

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//JAVA_OPTIONS -ea

public static List<Integer> evens(List<Integer> L) {
	return L.stream()
		.filter(n -> (n & 1) == 0)
		.toList();
}

public static Map<String, Integer> countWords(List<String> words) {
	Map<String, Integer> ret = new TreeMap<>();
	for (String word : words) {
		Integer count = ret.getOrDefault(word, 0);
		ret.put(word, count + 1);
	}
	return ret;

}

void main(String... args) {
	var nums = List.of(2, 5, 8, 7, 3, 10);
	var words = List.of("hello", "world", "hello", "cs61b", "world", "hello");

	var expected_evens = List.of(2, 8, 10);
	assert evens(nums).equals(expected_evens)
			: "evens 测试失败: 期望 %s, 实际得到 %s".formatted(expected_evens, evens(nums));

	var expected_words = Map.of("hello", 3, "cs61b", 1, "world", 2);
	assert countWords(words).equals(expected_words)
			: "countWords 测试失败: 期望 %s, 实际得到 %s".formatted(expected_words, countWords(words));

	System.out.println("所有测试通过 ✅");
}
```

---

Create a Dog class in python. A dog should have two properties: a `name` and a `size`. The dog class should have a method called `grow` that increases the dog's `size` by 1. If the user prints out a dog, it should print the name followed by "the size ", followed by the size, followed by the word " dog". For example, the code below should print "maya the size 1000 dog".

在 Python 中创建一个 Dog 类。Dog 类应该有两个属性：name（名字）和 size（体型/大小）。Dog 类应该有一个名为 grow 的方法，调用后让狗的 size 增加 1。当用户打印（print）一只狗时，应该打印出：名字 + "the size " + size + " dog"。例如，下面的代码应该打印出 "maya the size 1000 dog"。

[dog.py](./worksheet/dog.py)

```python
class Dog:
    def __init__(self,name,size):
        self.name = name
        self.size = size

    def grow(self):
        self.size += 1


    def __str__(self):
        return f"{self.name} the size {self.size} dog"

dogs = [Dog("maya", 1000), Dog("yipster", 5), Dog("scott", 25)]
print(dogs[0])
```

Below, we show the Java solution. As before, discuss with your group what you observe about the code.

下面我们展示 Java 解法。和之前一样，和你的小组成员讨论一下你从代码中观察到了什么。

[dog.java](./worksheet/dog.java)

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class Dog {
	String name;
	int size;

	Dog(String name, int size) {
		this.name = name;
		this.size = size;
	}

	void grow() {
		this.size += 1;
	}

	@Override
    public String toString() {
        return "%s the size %d dog".formatted(
            this.name,
            this.size
        );
    }
}

void main(String... args) {
	List<Dog> dogs = List.of(
			new Dog("maya", 1000),
			new Dog("yipster", 5),
			new Dog("scott", 25));

	System.out.println(dogs.get(0));
}
```

Using the example code from earlier in this worksheet, try to write a Java function below which returns the difference between the maximum and minimum item in a `List<Integer>`. You may assume the list has length at least 1. You can get the ith item of a `List` called `L` by calling `L.get(i)`. You can get the size of a list with `L.size()`. There are solutions that don't use either of these functions.

使用本 worksheet 前面的示例代码，尝试在下方编写一个 Java 函数，返回 `List<Integer>` 中最大值和最小值之间的差值。你可以假设列表长度至少为 1。你可以通过调用 `L.get(i)` 来获取 `List` 中第 i 个元素。你可以通过 `L.size()` 获取列表长度。也有不使用这两个函数的解法。

[min_max_diff.java](./worksheet/min_max_diff.java)

```java
public static int maxMinDiff(List<Integer> L) {
	int minNum = Integer.MAX_VALUE;
	int maxNum = Integer.MIN_VALUE;

	for (Integer num : L) {
		if (num > maxNum) {
			maxNum = num;
		}

		if (num < minNum) {
			minNum = num;
		}

	}
	return maxNum - minNum;
}

void main(String... args) {
	List<Integer> nums = List.of(-5, 10, 3, -8, 20, 0);
	assert maxMinDiff(nums) == 28;
}
```

Extra problem: Write a Java function that takes a `List<String>` and returns a map from each String to the list of Strings that immediately follow it (i.e. come right after it). For example, if the input list is `["I", "love", "java", "but", "I", "love", "python", "more"]`, then the output should be:

附加题：编写一个 Java 函数，接收一个 List<String>，返回一个映射，从每个字符串映射到紧接着它后面的字符串列表（即紧跟在它后面的那些字符串）。例如，如果输入列表是 ["I", "love", "java", "but", "I", "love", "python", "more"]，输出应该是：

```json
{
  "I": ["love", "love"],
  "love": ["java", "python"],
  "java": ["but"],
  "but": ["I"],
  "python": ["more"]
}
```

[list_to_map.java](./worksheet/list_to_map.java)这里为了输出我使用了第三方依赖[jackson](https://github.com/fasterxml/jackson)版本是第3版本

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS tools.jackson.core:jackson-databind:3.1.0

import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

public static Map<String, List<String>> listOfFollowers(List<String> x) {
	// Map<String, List<String>> ret = new TreeMap<>();
	// 保证插入顺序
	Map<String, List<String>> ret = new LinkedHashMap<>();
	for (int i = 0; i < x.size() - 1; i++) {
		String key = x.get(i);
		String value = x.get(i + 1);
		ret.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
	}
	return ret;
}

void main(String... args) {
	JsonMapper jsonMapper = JsonMapper.builder()
		.enable(SerializationFeature.INDENT_OUTPUT)
		.build();
	String msg = "I love java but I love python more";
	Map<String, List<String>> ret = listOfFollowers(List.of(msg.split(" ")));
	System.out.println(jsonMapper.writeValueAsString(ret));
}
/**output
 {
  "I" : [ "love", "love" ],
  "love" : [ "java", "python" ],
  "java" : [ "but" ],
  "but" : [ "I" ],
  "python" : [ "more" ]
}
 */
```
