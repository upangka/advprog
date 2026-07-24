// 等效
// let animal: Record<string, any> & Object = new Object();
let animal: Record<string, any> & Object = {};
animal.name = "Good";
console.log(animal); // { name: 'Good' }
console.log(typeof Object); // function

console.log(animal.hasOwnProperty("name")); // true

console.log(animal.__proto__ === Object.prototype); // true
console.log(typeof Object.prototype, Object.prototype); // object [Object: null prototype] {}
Object.getOwnPropertyNames(Object.prototype).forEach((propertyName) => {
  if (!propertyName.startsWith("__")) {
    // 处理ts报错
    const key = propertyName as keyof typeof Object.prototype;
    console.log(propertyName, " =>", typeof Object.prototype[key]);
  }
});

console.log("-----------------------");
Object.getOwnPropertyNames(Function.prototype).forEach((item) => {
  console.log(item);
});
