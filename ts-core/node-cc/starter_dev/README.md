# 测试案例

[hello.ts](./code/hello.ts)使用http模块搭建http服务，并通过读取文件内容进行返回，运行容器为node.js

```ts
// const http = require("node:http");
// const fs = require("node:fs");
import http from "node:http";
import fs from "node:fs";

const server = http.createServer();

server.on("request", (request, response) => {
  response.setHeader("Content-Type", "text/plain");
  const result = fs.readFileSync("msg.txt");
  response.write(result);
  response.end();
});

server.listen(4080, "localhost", () => {
  // console.log(`Server has started on: ${server.address()}`);
  // console.log(`Server has started on: ${JSON.stringify(server.address())}`);
  console.log(`Server has started on:`, server.address());
});
```

运行:

```ts
$ node hello.ts
Server has started on: { address: '127.0.0.1', family: 'IPv4', port: 4080 }
```

访问:

```sh
$ curl -i http://localhost:4080
HTTP/1.1 200 OK
Content-Type: text/plain
Date: Sat, 18 Jul 2026 15:00:19 GMT
Connection: keep-alive
Keep-Alive: timeout=5
Transfer-Encoding: chunked

Hello World from Node.js
```

# 类型提示

根据[node官网关于typescript](https://nodejs.org/learn/typescript/introduction)的介绍

要想有类型提示需要安装`@types/node`

> **when you use Node.js with TypeScript, you'll need type definitions for Node.js APIs. This is available via** `@types/node`

```sh
npm add --save-dev @types/node
```

而在[hello.ts](./code/)这个demo中尽管没有 `package.json` 和 `tsconfig.json`，使用现代 `import` 语法导入 `Node.js` 内置模块时，`VSCode` 的 `TypeScript 语言服务`能自动识别模块类型并提供智能补全；但使用 `CommonJS` 的 `require` 语法时，由于缺少类型声明上下文，`TypeScript` 会将模块推断为 `any` 类型，因此不会出现任何方法或属性的提示。这是因为 `import` 是 `ECMAScript` 标准语法，`TypeScript` 原生支持其模块解析；而 `require` 是 `Node.js` 特有的函数，在没有 `@types/node` 和 `tsconfig.json` 的情况下，语言服务无法获知其返回类型。两种方式在运行时都完全正常，差异仅存在于编辑器的智能提示层面。

除了因为`typescript`原生支持`import`之外，我在VSCode安装的[TypeScript 7](https://marketplace.visualstudio.com/items?itemName=TypeScriptTeam.native-preview)插件，帮我安装了`@types/node`相关的包。

```sh
pkmer@DESKTOP-2368UCO:node
$ pwd
/home/pkmer/.cache/typescript/7.0/node_modules/@types/node

pkmer@DESKTOP-2368UCO:node
$ ls
LICENSE             constants.d.ts            fs.d.ts                   module.d.ts       quic.d.ts            test.d.ts          util.d.ts
README.md           crypto.d.ts               globals.d.ts              net.d.ts          readline             timers             v8.d.ts
assert              dgram.d.ts                globals.typedarray.d.ts   os.d.ts           readline.d.ts        timers.d.ts        vm.d.ts
assert.d.ts         diagnostics_channel.d.ts  http.d.ts                 package.json      repl.d.ts            tls.d.ts           wasi.d.ts
async_hooks.d.ts    dns                       http2.d.ts                path              sea.d.ts             trace_events.d.ts  web-globals
buffer.buffer.d.ts  dns.d.ts                  https.d.ts                path.d.ts         sqlite.d.ts          ts5.6              worker_threads.d.ts
buffer.d.ts         domain.d.ts               index.d.ts                perf_hooks.d.ts   stream               ts5.7              zlib
child_process.d.ts  events.d.ts               inspector                 process.d.ts      stream.d.ts          tty.d.ts           zlib.d.ts
cluster.d.ts        ffi.d.ts                  inspector.d.ts            punycode.d.ts     string_decoder.d.ts  url.d.ts
console.d.ts        fs                        inspector.generated.d.ts  querystring.d.ts  test                 util
```

# node直接运行ts

**Node.js 原生支持 TypeScript**

`Node.js v22.18.0` 或更高版本，能够直接运行 `TypeScript` 文件而不需要任何标志,目前我的node版本

```sh
$ node -v
v26.3.0
```

[Node.js 官方Type stripping](https://nodejs.org/api/typescript.html#type-stripping)推荐一份 `tsconfig.json` 配置，用于让 `tsc` 的类型检查行为与 `Node.js` 的`(Type stripping)类型剥离`行为完全对齐。其中的 `"erasableSyntaxOnly": true` 是关键，它会禁止使用 `enum`、`参数属性`等无法被剥离的语法，从而在编辑阶段就防止运行时错误。但学习初期可以跳过此配置，专注于 `Node.js` 核心模块本身。

```json
{
  "compilerOptions": {
    "noEmit": true, // Optional - see note below
    "target": "esnext",
    "module": "nodenext",
    "rewriteRelativeImportExtensions": true,
    "erasableSyntaxOnly": true,
    "verbatimModuleSyntax": true
  }
}
```

| 配置项                              | 作用                                                 | 对你当前学习的影响                                                                                                                       |
| ----------------------------------- | ---------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------- |
| `"noEmit": true`                    | 告诉 tsc 不要生成任何 .js 输出文件。                 | 你使用 `node hello.ts` 直接运行，不需要 tsc 生成文件，开启此项可以节省编译时间。                                                         |
| `"target": "esnext"`                | 编译目标为最新的 ECMAScript 版本。                   | 让 tsc 假设你的代码运行在支持最新 JS 特性的环境中，与 Node.js 的现代支持对齐。                                                           |
| `"module": "nodenext"`              | 使用 Node.js 最新的模块解析规则。                    | 告诉 tsc 如何解析 `import` 和 `export`，与 Node.js 原生 ESM 支持一致。                                                                   |
| `"rewriteRelativeImportExtensions"` | 允许在导入时省略或改写文件扩展名。                   | 比如你写 `import "./utils"`，Node.js 会尝试 `utils.js` 或 `utils.ts`，此项让 tsc 同样理解这种行为。                                      |
| `"erasableSyntaxOnly": true`        | **核心限制**：只允许“可剥离”的 TypeScript 语法。     | 如果代码中使用了 `enum`、参数属性、`namespace` 等需要生成运行时代码的语法，tsc 会直接报错，告诉你这些语法在 Node.js 原生运行中不受支持。 |
| `"verbatimModuleSyntax": true`      | 强制保持 `import` 和 `export` 语法原样，不进行转换。 | 确保 tsc 不会把 `import` 重写为 `require`，与 Node.js 的 ESM 行为保持一致。                                                              |

---

- 如果你不配置：VSCode 可能不会对 enum 或 namespace 报错，但 Node.js 运行时会失败，你会陷入“编辑器说没问题，但跑起来就报错”的困惑中。

- 如果你配置了：VSCode 会直接在你的代码中对这些不支持的语法标红，在编写阶段就拦截问题，避免运行时报错。

> **建议**
>
> 对于学习阶段，完全可以暂不创建 `tsconfig.json`，因为：
>
> 1. 在学习 `Node.js` 核心概念，重点在 `http`、`fs`、`stream` 等模块的使用，暂时不会遇到 `enum` 或 `namespace` 这些复杂的`TS`语法
> 2. 追求**最快反馈**，多一个配置文件就需要多一步理解和维护。

## 关于tsconfig.json

`tsconfig.json` 里的配置，本质上就是 `tsc` 命令后面那些参数的“配置文件版本”。

- 命令行参数：灵活，适合临时调整或脚本化。
- `tsconfig.json`：持久化，适合项目根目录统一管理，让所有开发者（和 CI 环境）使用相同配置。

| 方式            | 形式                                | 适用场景                                       |
| --------------- | ----------------------------------- | ---------------------------------------------- |
| 命令行参数      | `tsc --noEmit --target esnext`      | 临时覆盖、CI/CD 脚本、快速测试单个配置         |
| `tsconfig.json` | 将 `--noEmit` 写成 `"noEmit": true` | 项目级固定配置、团队协作、避免每次输入长串命令 |

它们是等价的。`tsc` 命令会读取 `tsconfig.json` 中的设置作为默认值，而命令行上显式传入的参数会覆盖配置文件中的同名设置。

更多的对应关系

| 配置项                   | 命令行写法                 | tsconfig.json 写法               |
| ------------------------ | -------------------------- | -------------------------------- |
| 禁止输出编译文件         | `tsc --noEmit`             | `"noEmit": true`                 |
| 指定 ECMAScript 目标版本 | `tsc --target esnext`      | `"target": "esnext"`             |
| 指定模块系统             | `tsc --module nodenext`    | `"module": "nodenext"`           |
| 仅允许可擦除语法         | `tsc --erasableSyntaxOnly` | `"erasableSyntaxOnly": true`     |
| 指定项目配置文件         | `tsc --project ./src`      | 指定配置文件路径，不在命令行体现 |
