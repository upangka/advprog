In JavaScript, a class is a kind of function.

# class概览

> 说明：类属性的箭头函数，`this`
>
> 1. 箭头函数不绑定自己的 `this`
> 2. 它的 `this` 继承自定义时外层作用域的 `this`
> 3. 在类字段中，外层作用域是 `new MyClass()` 的执行上下文，`this` 指向新创建的实例

```js
class MyClass {
  prop = value; // property
  // 绑定this永远为new MyClass 实例
  proprOfArrowFunc = () => console.log(this.prop)
  // class methods
  constructor(...) { // constructor
    // ...
  }

  method(...) {} // method

  get something(...) {} // getter method
  set something(...) {} // setter method

  [Symbol.iterator]() {} // method with computed name (symbol here)
  // ...
}
```

> 小结：
>
> 1. `prop` 和 `propOfArrowFunc` 这类类字段（`class fields`） 被存储在每个实例对象自身上，而不是 `MyClass.prototype `上,具有隔离性
> 2. 类方法定义在 `prototype` 上，是为了所有实例共享，节省内存。
> 3. 箭头函数类字段定义在实例上，是为了捕获当前实例的 `this`，确保回调中 `this` 指向正确。
