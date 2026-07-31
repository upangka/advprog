静态方法永远属于类，即使通过 `null` 引用调用也不会报错，但这是一种危险的编码习惯，**应该始终使用类名调用静态方法**，避免混淆和潜在的错误理解。

[exercise_01.java](./code/exercise_01.java)

```java
class Human {
	static void ponder(int x) {
		IO.println(x);
	}
}

void main(String... args) {
	Human h = null;
	Human.ponder(2); // 输出：2
	h.ponder(3); // 输出：3（注意：h 是 null，但不会抛出异常！）
}
```

编译器会忽略实例引用 `h` 的值（即使是 `null`），直接替换为类名调用。

```java
h.ponder(3);   // 编译后等价于 → Human.ponder(3);
```
