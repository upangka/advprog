# Buffer属于堆外内存

避免 V8 GC 压力：大型二进制数据（如图片、文件、网络数据包）如果放在 V8 堆中，会频繁触发垃圾回收，影响性能。堆外内存不受 GC 直接影响。

高效 I/O 操作：堆外内存可以直接与操作系统交互（通过 read()、write() 系统调用），无需在 V8 堆和本地内存之间复制数据（零拷贝）。

与底层系统交互：Node.js 的底层（libuv）和 C++ 扩展需要操作原始内存，Buffer 提供了这样的接口。

# Buffer的内存结构

```sh
+-------------------+
|   V8 堆内存        |  <- JavaScript 对象（{ }, [ ], 字符串）
|   (受 GC 管理)     |
+-------------------+
|   Buffer 对象      |  <- 这是一个小的 JavaScript 对象，包含指针
|   (在 V8 堆中)     |  <- 它指向堆外的一块内存
+-------------------+
         |
         v
+-------------------+
|   堆外内存块       |  <- 实际的二进制数据存储位置
|   (由 C++ 管理)    |  <- 通过 `malloc` 或 `alloc` 分配
+-------------------+
```

# 编码解码

看看存储的二进制数据。主要是解决中文存储的UTF-8编码后的字节。[exercise_03.ts](./code/exercise_03.ts)

```ts
import { Buffer } from "node:buffer";

console.log(Buffer.from("Hi,深圳", "utf-8")); // 输出: <Buffer 48 69 2c e6 b7 b1 e5 9c b3>

// 分配9字节的缓冲区,其中字节存储的是utf-8编码的字符串"Hi,深圳"
const buf = Buffer.alloc(9);

buf[0] = 0x48; // 'H'
buf[1] = 0x69; // 'i'
buf[2] = 0x2c; // ,
// 深
buf[3] = 0xe6; //
buf[4] = 0xb7; //
buf[5] = 0xb1; //
// 圳
buf[6] = 0xe5; //
buf[7] = 0x9c; //
buf[8] = 0xb3; //

console.log(buf); // 输出: <Buffer 48 69 2c e6 b7 b1 e5 9c b3>
console.log(buf.toString("utf-8")); // 输出: "Hi,深圳"
```
