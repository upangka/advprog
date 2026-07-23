# Stream

**Stream**: an abstract interface for working with **streaming data** in Node.js

![](./images/stream_architecture.png)

**流水的背后，本质上就是分块处理**

1. 分块处理（Chunking）：是 Stream 的工作方式（How）。它把数据切成一块块（Chunk），逐块处理。这是技术实现。
2. 流水模式（Streaming）：是分块处理带来的宏观效果（What）。因为分块足够小、处理足够快，从宏观上看，数据就像“连绵不断的水流”。这是你感受到的体验。

## WritableStream

Stream 性能高的根本原因，本质上是通过减少系统调用和内存占用，并利用流水线模式，实现了对系统资源的最大化利用。

（补充：这里的减少内存占用是指，Stream 真正的对比对象是：全部加载到内存再处理，比如为了复制一个10GB的文件，全部加载到内存，直接干报废。）

![](./images/writeable_stream.png)

## ReadableStream

![](./images/readable_stream.png)

# Stream 提供的背压(backpressure)控制

**Backpressure（背压）**：可写流的一种自动调节机制——当生产者写入速度超过消费者处理速度时，通过 write() 返回 false 暂停生产者，待缓冲区排空后触发 drain 事件恢复写入，确保内存占用可控。

`false + drain` 机制就是 `Stream` 提供的背压控制，目的是防止内存被撑爆。因为当你写入的数据量到达`highWaterMark`的时候，仍然可以继续写，此时内存会扩张，最后导致内存会撑爆。

`highWaterMark` 是 Node.js 给你的一根“水位警戒线”。它告诉你：“嘿，水位到这里了，建议你暂停一下。” 但如果你不听，水（数据）还是会继续往池子里倒（缓冲区会继续扩张），直到池子彻底溢出（内存耗尽）。

![alt text](deepseek_mermaid_20260723_2e5371.png)

`write()` 的返回值表示的是 “写入缓冲区之后，缓冲区是否还有剩余空间”

| 返回值  | 含义                                                                                  |
| :------ | :------------------------------------------------------------------------------------ |
| `true`  | 数据已写入缓冲区，且**缓冲区尚未达到水位线**，你可以继续放心地写入。                  |
| `false` | 数据已写入缓冲区，但**缓冲区已达到或超过水位线**，你应该暂停写入，等待 `drain` 事件。 |

```ts
writableHighWaterMark; // 水位线
writableLength; // 当前缓存区已经写入了多少
```

无论从时间（批量写）还是从空间占用内存角度，以下代码都是相比于[性能对比](./性能对比.md)的几个案例，最优的解

# 扩展

1. Java 是“装饰器模式”：通过层层包装类来组合功能。它的优点是很直观，你可以看到每一步的“包裹”，但代价是代码很长，类名也很长。

2. Node.js 是“工厂模式 + 管道模式”：它不让你层层包裹对象，而是通过 .pipe() 方法将不同的流实例连接起来。它的目标是让代码更简洁、更符合“流水线”的直觉。

从功能上讲，readable.pipe(writable) 其实就是 Java 中 new BufferedReader(new FileReader(...)) 的 Node 版写法。
