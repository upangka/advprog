
[builtins.pyi](https://github.com/python/typeshed/blob/a8834fcd46339e17fc8add82b5803a1ce53d3d60/stdlib/2and3/builtins.pyi)

# Overloaded Signatures

 在 Python的类型提示里有`@overload`,但是不要把 Python 的 @overload 理解为像Java那样“定义多个方法”，而要理解为**给同一个方法贴多个类型标签**。
 值得一提的是Java有个注解`@Override`这是重写，而`@overload`是重载。

- 简单的理解: **@overload就是方法的重载，只不过在一个具体方法中实现**，
- 专业的角度就是: 静态检查器static typing checker能够根据我们编写的@overload函数签名，进行静态检查，**能够更具用户输入的参数类型，正确推导出来返回的类型****



[mysum.py](./code/override/mysum.py)的实现可以看到，里面做了if分支的判断。这与Java的实现方式形成鲜明的对比，因为Java是单独方法进行实现的。

```python
MISSING = object()
MSG = "max() arg is an empty sequence"

from typing import overload, Protocol, TypeVar, Union
from collections.abc import Iterable, Callable


class SupportLessThan(Protocol):
    def __lt__(self, other, /) -> bool: ...


T = TypeVar("T")
LT = TypeVar("LT", bound=SupportLessThan)
DT = TypeVar("DT")


# 支持__lt__,但是没有key和default,注意这里是怎么禁用传入key的
# @overload
# def mymax(_arg1: LT,/, *args: LT, key: None = ...) -> LT: ...
# # 等价
# @overload
# def mymax(first: LT, *args: LT, key: None = ...) -> LT: ...
@overload
def mymax(_arg1: LT, _arg2: LT, /, *args: LT, key: None = ...) -> LT: ...
@overload
def mymax(_iterable: Iterable[LT], /, *, key: None = ...) -> LT: ...
@overload
def mymax(_iterable: Iterable[T], /, *, key: Callable[[T], LT]) -> T: ...
@overload
def mymax(_arg1: T, /, *args: T, key: Callable[[T], LT]) -> T: ...
@overload
def mymax(
    _iterable: Iterable[LT], /, *, key: None = ..., default: DT
) -> Union[LT, DT]: ...  # 有默认值但是没有key
@overload
def mymax(
    _iterable: Iterable[T], /, *, key: Callable[[T], LT], default: DT
) -> Union[T, DT]: ...  # 有默认值也有key
def mymax(first, *args, key=None, default=MISSING):
    if args:
        series = args
        candidate = first
    else:
        series = iter(first)
        try:
            candidate = next(series)
        except StopIteration:
            if default is not MISSING:
                return default
            raise ValueError(MSG) from None

    if key:
        # assert callable(key),"Not callable"
        candidate_key = key(candidate)
        for current in series:
            current_key = key(current)
            if current_key > candidate_key:
                candidate_key = current_key
                candidate = current
    else:
        for current in series:
            if current > candidate:
                candidate = current
    return candidate
```

# Java 重载等价实现

> 在我实现完成之后，发现要到达同样的效果，还是python的实现简单些，😂

- [dev.java](https://dev.java/)
- [jdk.java.net](https://jdk.java.net/)
- [oracle free course](https://mylearn.oracle.com/ou/course/overview/79727)

vscode设置WSL的JDK `Java: Configure Java Runtime`

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

不然泛型擦除
```java
// 调用内部的方法 this.<T, T>mymax
return this.<T, T>mymax(iterable, Function.identity());
// 外部使用 new MySum().<Student, Integer>mymax
Student longestName = new MySum().<Student, Integer>mymax(students, s -> s.name().length());
```


[MySum.java](./code/override/MySum.java)


```java
import java.util.function.Function;

/**
 * JDK 26
 * java MySum.java
 */
public class MySum {
    private static final Object MISSING = new Object();
    public static final String MSG = "max() arg is en empty sequence";

    /*
     * 等价
     * 
     * @overload
     * def mymax(_arg1: LT,/, *args: LT, key: None = ...) -> LT: ...
     * 
     * @overload
     * def mymax(first: LT, *args: LT, key: None = ...) -> LT: ...
     * 
     * @overload
     * def mymax(_arg1: LT,_arg2:LT,/, *args: LT, key: None = ...) -> LT:
     * ...
     */
    public <T extends Comparable<? super T>> T mymax(T first, T[] rest) {
        return this.mymax(first, rest, t -> t);
    }

    /*
     * 等价
     * def mymax(_arg1: T, /, *args: T, key: Callable[[T], LT]) -> T: ...
     */
    public <T, R extends Comparable<? super R>> T mymax(
            T first, T[] rest,
            Function<? super T, ? extends R> key) {

        T condicate = first;
        R condicateKey = key.apply(condicate);
        for (T current : rest) {
            R currentKey = key.apply(current);
            if (currentKey.compareTo(condicateKey) > 0) {
                condicate = current;
                condicateKey = currentKey;
            }
        }
        return condicate;
    }

    /*
     * 等价
     * 
     * @overload
     * def mymax(_iterable: Iterable[LT], /, *, key: None = ...) -> LT: ...
     */
    public <T extends Comparable<? super T>> T mymax(Iterable<T> iterable) {
        // <T, T> 是 显式类型实参（explicit type argument）避免泛型擦除
        return this.<T, T>mymax(iterable, Function.identity());
    }

    /*
     * 等价
     * 
     * @overload
     * def mymax(_iterable: Iterable[T], /, *, key: Callable[[T], LT]) -> T: ...
     */
    public <T, R extends Comparable<? super R>> T mymax(
            Iterable<T> iterable,
            Function<? super T, ? extends R> key) {

        var iter = iterable.iterator();
        if (!iter.hasNext()) {
            throw new IllegalArgumentException(MSG);
        }

        T candidate = iter.next();
        R candidate_key = key.apply(candidate);
        while (iter.hasNext()) {
            var current = iter.next();
            var current_key = key.apply(current);
            if (current_key.compareTo(candidate_key) > 0) {
                candidate_key = current_key;
                candidate = current;
            }
        }
        return candidate;
    }

    /*
     * 等价
     * 
     * @overload
     * def mymax(
     * _iterable: Iterable[LT], /, *, key: None = ..., default: DT
     * ) -> Union[LT, DT]: ...
     * 
     */
    public <T extends Comparable<? super T>, DT> Object mymax(
            Iterable<T> iterable,
            DT defaultValue) {
        return this.mymax(iterable, t -> t, defaultValue);
    }

    /*
     * 等价
     * 
     * @overload
     * def mymax(
     * _iterable: Iterable[T], /, *, key: Callable[[T], LT], default: DT
     * ) -> Union[T, DT]: ...
     * 
     */
    public <T, R extends Comparable<? super R>, DT> Object mymax(
            Iterable<T> iterable,
            Function<? super T, ? extends R> key,
            DT defaultValue) {
        var iter = iterable.iterator();
        if (!iter.hasNext()) {
            return defaultValue;
        }

        return this.mymax(iterable, key);
    }

}
```

测试文件[TestMySum.java](./code/override/TestMySum.java)