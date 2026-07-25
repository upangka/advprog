objects are used to store keyed collections of various data

试图把 `__proto__` 设置为非对象值时，JavaScript **静默失败**(silently fails)的行为。

```ts
> let obj = {}
undefined
> obj.__proto__ = 5 // assign a number
5
> obj.__proto__ // 没有变成预期的5还是一个对象
[Object: null prototype] {}
> Object.setPrototypeOf(obj,5)  // 官方推荐的api，可以看到立即报错
Uncaught TypeError: Object prototype may only be an Object or null: 5
    at Object.setPrototypeOf (<anonymous>)
```

`__proto__` 本质上是一个访问器属性（getter/setter），而不是一个普通的可任意赋值的属性。它的 setter 内部有类型检查逻辑：

1. 如果赋值为对象或 `null`，则正常设置原型。
2. 如果赋值为非对象值（数字、字符串、布尔值、undefined），则直接忽略赋值操作，不做任何事情，也不抛出错误（非严格模式下）

原型链必须是一个对象（或 null）才能正常工作。如果允许基本类型作为原型，JavaScript 引擎就无法在原型链上查找属性（因为基本类型没有属性表）。所以语言设计者选择"静默忽略"来避免程序崩溃，但这确实容易造成困惑。

> **建议**： 不要在代码中直接操作 `__proto__`（官方已废弃），用 `Object.getPrototypeOf()` 和 `Object.setPrototypeOf()` 替代。

1. 使用 `Object.setPrototypeOf(obj, 5)`：它会抛出异常（TypeError），让你立刻发现问题。
2. 使用 `Object.create(proto)`：在创建对象时就指定原型，避免后续修改。
