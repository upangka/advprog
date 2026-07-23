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

# 几段代码的性能对比

## 异步的串行化

[exercise_02.ts](./code/exercise_02.ts)执行用来90s,尽管是异步，但是`await` 强制序列化执行，无法利用并发

```ts
import fs from "node:fs/promises";
import { Buffer } from "node:buffer";

// writeMany: 1:30.343 (m:ss.mmm)
console.time("writeMany");
const fd = await fs.open("./dist/test.txt", "w");
for (let i = 0; i <= 1_000_000; i++) {
  await fd.write(`${i} `);
}
await fd.close();
console.timeEnd("writeMany");
```

1. 100 万次 await + 100 万次 `fd.write`，每次都是**系统调用**。
2. 每次写入都涉及用户态/内核态切换。
3. 虽然 `fd.write` 是异步的，但 `await` **强制序列化执行，无法利用并发**。

Node.js `fd.write` 系统调用流程:

4. **用户态**：Node.js 的 JavaScript 代码调用 `fd.write`。
5. **用户态**：Node.js 的 C++ 层（libuv）封装这个调用，准备发起系统调用。
6. **用户态 → 内核态**：libuv 调用操作系统提供的 `write` 系统调用。此时 CPU 从用户态切换到内核态，操作系统接管控制权。
7. **内核态**：操作系统将数据从用户空间（Node.js 的 Buffer）复制到内核缓冲区，然后排队等待写入磁盘。
8. **内核态 → 用户态**：`write` 系统调用返回，CPU 切换回用户态，Node.js 恢复执行。
9. **用户态**：`await` 等待的 Promise 被 resolve，执行下一行代码。

> **每次 `fd.write` 都会经历一次完整的切换：用户态 → 内核态 → 用户态。**

## 同步版本（比异步串行化快 50 倍）

上面的异步版本虽然标记为 async，但因为使用了 await，实际上强制串行执行，并没有获得并发优势。反而因为：

1. 每次 fd.write 都要创建一个 Promise 对象
2. 操作被提交到 libuv 线程池，增加了线程间通信开销
3. 完成后需要通过 事件循环 通知主线程 resolve

而同步版本[exercise_03.ts](./code/exercise_03.ts) `writeSync` 直接发起系统调用，没有任何中间层开销。

```ts
import fs from "node:fs";
import { Buffer } from "node:buffer";

// writeMany: 1.920s
console.time("writeMany");
fs.open("./dist/test.txt", "w", (err, fd) => {
  for (let i = 0; i <= 1_000_000; i++) {
    fs.writeSync(fd, `${i} `);
  }

  fs.closeSync(fd);
  console.timeEnd("writeMany");
});
```

在循环中逐次执行**大量小写**入时，`fs.promises` 的 `await write` 反而比同步 `writeSync` 慢得多。因为多了"主线程 ↔ 线程池"的往返开销，而同步版本直接走系统调用，路径更短。
