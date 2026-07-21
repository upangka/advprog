# ascii码

linux下通过`man ascii`查看

![](./images/man_ascii.png)

[exercise_01.ts](./code/exercise_01.ts)

```ts
// 字符H二进制的表示形式
let H_binary = 0b01001000;
console.log(H_binary); // 72

let H_hex = 0x48;
console.log(H_hex); // 72

// 对应为ascii码表 man ascii
const H_char = String.fromCharCode(H_binary);
console.log(H_char); // H

// 获得'H'的ascii码
const H_ascii = "H".charCodeAt(0);
console.log(H_ascii); // 72
```

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

用Buffer
