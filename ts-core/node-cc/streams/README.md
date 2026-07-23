# 基准测试

[exercise_01.ts](./code/exercise_01.ts)

```ts
console.time("writeMany");
let a = 3 + 1e10;
console.timeEnd("writeMany");
```

运行

```sh
$ node exercise_01.ts
writeMany: 0.006ms
```
