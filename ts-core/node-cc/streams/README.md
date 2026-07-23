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

好的，我来帮你将这段新代码的分析补充到笔记中，保持你原有的风格和结构。

## 异步非阻塞（不等待完成）—— 极具欺骗性的“快”

[exercise_04.ts](./code/exercise_04.ts)

```ts
import fs from "node:fs";
import { Buffer } from "node:buffer";

// writeMany: 1.895s
console.time("writeMany");
fs.open("./dist/test.txt", "w", (err, fd) => {
  for (let i = 0; i <= 1_000_000; i++) {
    fs.write(fd, `${i} `, (err) => {});
  }
  console.timeEnd("writeMany");
});
```

**运行结果**：`writeMany: 1.895s`

**这个时间意味着什么？**

1. **1.895 秒是“提交任务”的时间，而不是“写入完成”的时间。**
   - `fs.write(fd, data, callback)` 是异步的，调用后**立即返回**，不会等待数据真正写入磁盘。
   - 循环在 1.895 秒内完成了 100 万次 `fs.write` 调用，**仅表示 100 万个写入请求已被提交给 libuv 线程池**。

2. **数据完整性无法保证。**
   - 由于没有 `close` 操作，文件句柄在回调结束后仍然处于打开状态。
   - 虽然主线程已结束，但事件循环仍会等待未完成的异步任务。然而，这些写入操作会继续在后台排队执行，最终仍会将数据写入文件，但顺序和完整性都无法保证。
   - 如果进程在写入完成前退出，数据可能丢失或截断。

3. **为什么看起来比同步版本还快？**
   - 同步版本 `writeSync` 的 1.92 秒是**实际完成写入的时间**（因为 `closeSync` 阻塞等待所有写入完成）。
   - 而这里的 1.895 秒**没有等待任何写入完成**，只是把任务“扔”了出去。

**真相**：这段代码的 1.895 秒，是**假快**。它并没有完成 100 万次写入，只是完成了 100 万次“安排写入”的操作。

## 小结

| 方式                        | 耗时        | 数据完整性    | 对应文件         | 核心分析                                                                                  |
| :-------------------------- | :---------- | :------------ | :--------------- | :---------------------------------------------------------------------------------------- |
| `await write`（异步串行化） | **~90s**    | ✅ **完整**   | `exercise_02.ts` | 每次写入都**阻塞等待**系统调用完成，虽然数据安全，但**串行化**执行导致性能极差。          |
| `writeSync`（同步循环）     | **~1.92s**  | ✅ **完整**   | `exercise_03.ts` | **同步阻塞**，直接发起系统调用，无中间层开销，速度最快且数据完整。                        |
| `write`（不关闭，无等待）   | **~1.895s** | ❌ **不完整** | `exercise_04.ts` | **异步非阻塞**，`fs.write` 立即返回，循环只负责**提交任务**，未等待完成，数据大概率丢失。 |
