`JavaScript/TypeScript` 想象成一个只有“大脑”（逻辑和类型）的生物，而 `Java/Python/C++` 是天生自带“手脚”（标准库）的完整个体。`TS` 的“手脚”必须由宿主环境临时安装——在 `Node.js` 里装的是文件操作的“手”，在`浏览器`里装的是操作网页的“手”。这种“分离”的特性，是 `JS/TS` 生态最独特、也最容易被初学者忽略的本质。

`Java/Python/C++` 是“封闭式”语言，而 `TypeScript` 是“开放式”语言。

1. `Java/Python`：语言 = 语法 + 标准库 + 虚拟机/解释器。它们是“一站式”的。
2. `TypeScript`： 语言 = 仅仅只是语法。`TypeScript 编译器（tsc）`只干一件事：把 .ts 文件编译成 .js 文件。至于编译之后，这个 .js 文件是拿去给浏览器跑，还是给 Node.js 跑，还是给 Deno/Bun 跑，`TypeScript` 不管。

`JavaScript/TypeScript` 的“手脚”并非只有 `Node.js` 这一套。近年来，为了解决 `Node.js` 在安全性和效率上的历史问题，社区又诞生了 `Deno` 和 `Bun` 这两个新的宿主环境。它们都承诺能直接运行 `TypeScript` 文件，并提供各自的“手脚”`API`。`Deno` 主打安全与现代化，而 `Bun` 则以极致的速度和“一站式”工具链见长。

- [宿主环境Node.js](./node-cc/starter_dev/README.md)
- [宿主环境brower](./brower/start_dev/)

- [字符集与编码](./node-cc/buffer/字符集与编码.md)
- [Buffer](./node-cc/buffer/README.md)
- [fs模块相关](./node-cc/fs_m/README.md)
- [streams相关](./node-cc/streams/README.md)
  - [性能对比](./node-cc/streams/性能对比.md)
