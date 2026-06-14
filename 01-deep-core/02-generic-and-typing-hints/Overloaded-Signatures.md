
[builtins.pyi](https://github.com/python/typeshed/blob/a8834fcd46339e17fc8add82b5803a1ce53d3d60/stdlib/2and3/builtins.pyi)

# Overloaded Signatures

 在 Python的类型提示里有`@overload`,但是不要把 Python 的 @overload 理解为像Java那样“定义多个方法”，而要理解为**给同一个方法贴多个类型标签**。
 值得一提的是Java有个注解`@Override`这是重写，而`@overload`是重载。



[mysum.py](./code/override/mysum.py)


# Java

- [dev.java](https://dev.java/)
- [jdk.java.net](https://jdk.java.net/)
- [oracle free course](https://mylearn.oracle.com/ou/course/overview/79727)

vscode Java: Configure Java Runtime


[MySum.java](./code/override/MySum.java)


关键认知

`? extends R` 和 `? super R` 是“通配符”
它们不是“类型”，而是类型约束。它们描述的是：某个地方可以接受什么范围的实际类型。

```java
public static <T, R extends Comparable<? super R>> T max(
    T first, T second, T... rest,
    Function<? super T, ? extends R> key)
```

`R extends Comparable<? super R>` 保证 R 自己能比较（这是整个比较机制的基础）

| 写法 | 含义 | 可接受的实际类型 |
|------|------|------------------|
| `R` | 精确匹配 | 只能是 `R` 本身 |
| `? extends R` | 上界通配符 | `R` 或 `R` 的任何子类 |
| `? super R` | 下界通配符 | `R` 或 `R` 的任何父类 |