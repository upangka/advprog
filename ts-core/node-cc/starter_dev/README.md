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

Hello World from Node.jss
```
