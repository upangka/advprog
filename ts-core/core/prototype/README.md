# Object.prototype

在Java中Object是所有类的基类，但是在JS中他是一个构造函数，可以使用`new Object()`来创建一个对象，但是平常我们都使用字面量`{}`来快速创建对象。

在TS中Object被当成类型。

```ts
// 等效
// let animal: Record<string, any> & Object = new Object();
let animal: Record<string, any> & Object = {};
animal.name = "Good";
console.log(animal); // { name: 'Good' }
console.log(typeof Object); // function

console.log(animal.hasOwnProperty("name")); // true
```

从下面的图片图片中可以看到`animal`对象默认就有一些方法，是因为对象默认通过`__proto__`继承了`Object.prototype`对象，而该对象默认共享了这些方法。
![](./images/prototypes_funcs.png)

```ts
console.log(animal.__proto__ === Object.prototype); // true
console.log(typeof Object.prototype, Object.prototype); // object [Object: null prototype] {}
Object.getOwnPropertyNames(Object.prototype).forEach((propertyName) => {
  if (!propertyName.startsWith("__")) {
    // 处理ts报错
    const key = propertyName as keyof typeof Object.prototype;
    console.log(propertyName, " =>", typeof Object.prototype[key]);
  }
});
/**输出的方法
constructor  => function
hasOwnProperty  => function
isPrototypeOf  => function
propertyIsEnumerable  => function
toString  => function
valueOf  => function
toLocaleString  => function
*/
```

`Object`是函数，`Object.prototype`是一个对象，这个对象里面有很多共享的方法，JS中的其他对象都继承这个对象，所以`animal.__proto__ === Object.prototype`为`true`
