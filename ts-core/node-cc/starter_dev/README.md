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

**类型提示**：尽管没有 `package.json` 和 `tsconfig.json`，使用现代 `import` 语法导入 `Node.js` 内置模块时，`VSCode` 的 `TypeScript 语言服务`能自动识别模块类型并提供智能补全；但使用 `CommonJS` 的 `require` 语法时，由于缺少类型声明上下文，`TypeScript` 会将模块推断为 `any` 类型，因此不会出现任何方法或属性的提示。这是因为 `import` 是 `ECMAScript` 标准语法，`TypeScript` 原生支持其模块解析；而 `require` 是 `Node.js` 特有的函数，在没有 `@types/node` 和 `tsconfig.json` 的情况下，语言服务无法获知其返回类型。两种方式在运行时都完全正常，差异仅存在于编辑器的智能提示层面。

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
