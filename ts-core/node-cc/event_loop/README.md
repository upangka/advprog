阻塞主线程或事件循环的demo

```js
setTimeout(() => {
  console.log("Done");
}, 1);

for (let i = 0; i < 9_000_000_000; i++) {}
```
