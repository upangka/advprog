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

```ts
let pure = Object.create(null); // 真正的"纯净"容器，没有 toString 等
```

---

In JavaScript, keyword this behaves unlike most other programming languages. It can be used in any function, even if it’s not a method of an object.

# this

Another programming language (like Python), then you are probably used to the idea of a “bound this”, where methods defined in an object always have this referencing that object.

In JavaScript this is “`free`”, its value is evaluated at call-time and does not depend on where the method was declared, but rather on what object is “before the dot”.

js代码

```js
> let user = {
|    f(){}
| }
> user.f
[Function: f]
> user.f.bind(this)
[Function: bound f]
```

python代码

```python
>>> class User:
...     def f(self):
...         ...
...
>>> User.f
<function User.f at 0x7f2d1d087420>
>>> u = User()
>>> u.f
<bound method User.f of <__main__.User object at 0x7f2d1d0316a0>>
```

Python中的bound的，注意Python因为有类，所以能够直接使用User.f（没有绑定实例），实例对象u.f则是绑定了u，所以是bound。而JS中是没有类的，它只是语法糖。

```js
> class User{
| f(){}
| }

> User.f
undefined
> // 方法挂载在原型上
> User.prototype.f
[Function: f]
```

```python
User (函数对象)
  │
  ├── __proto__ → Function.prototype  ←─ 作为"函数"的继承
  │
  └── prototype → { constructor: User, f: ƒ }  ←─ 作为"构造函数"的实例模板
                     │
                     └── __proto__ → Object.prototype
```

`__proto__`（dunder proto）—— 对象的原型链接，指向该对象的父级原型对象。每个对象都有这个属性（实际上是一个 getter/setter）。

`prototype` —— 函数的原型对象。只有函数才有这个属性，用于设置通过该函数创建的实例的原型。
