# html+ts

[html+typescript](./code/origin/)网页版本

使用`tsc`将`ts`代码转化为`js`代码

`tsc --init`创建`tsconfig.json`,默认的module转化是node的commonjs形式的。需要改为`esnext`

[tsconfig.json](./code/origin/tsconfig.json)

```json
// "module": "nodenext",
"module": "esnext",
// 指定输出js的地方，方便gitignore进行忽略
"outDir": "./dist",
```

创建`index.html`指定`js`，这里采用模块化的方式进行引入js

[index.html](./code/origin/index.html)

```html
<script src="./dist/index.js" type="module" />
</body>
```

测试用的[index.ts](./code/origin/index.ts)

```ts
const container = document.getElementById("container")!;
const msg = "Hello World from Browser.";
container.innerText = msg;
console.log(msg, container);
```

开启`tsc -w`,当我们修改修改`.ts`文件的时候，会实时进行编译输出`js`。

生成的`index.js`如下，注意文件底部有一个特别的注释`//# sourceMappingURL`这是source map.方便debugger原代码调试的。

```js
const container = document.getElementById("container");
const msg = "Hello World from Browser.";
container.innerText = msg;
console.log(msg, container);
export {};
//# sourceMappingURL=index.js.map
```

此外还生成了其他的`source map`文件

```sh
.
├── index.d.ts
├── index.d.ts.map
├── index.js
└── index.js.map
```

`Source Map`是一个映射文件，它的作用是建立**“编译/转换后的代码”与“原始源代码”之间的对应关系**。

在 `HTML + TypeScript` 这种开发模式下，我们直接编写 `.ts` 文件，但浏览器实际运行的是由 `tsc` 编译生成的 `.js` 文件。当我们在浏览器开发者工具中调试时，直接看编译后的 `.js` 代码会非常困难——它丢失了类型、可能被转换，而且与我们实际编写的代码不一致。

`Source Map` 就是为了解决这个问题而存在的。它记录了编译后代码的每一个位置，对应到原始源代码的哪个文件、哪一行、哪一列。

在编译生成的 index.js 文件底部，有这样一行特殊注释：

```js
//# sourceMappingURL=index.js.map
```

`sourceMappingURL` 就是浏览器识别 `Source Map` 的标志。当浏览器执行 `JavaScript` 时，如果检测到这个注释，就会自动尝试加载对应的 `.map` 文件，并利用其中的映射信息，在开发者工具的`Sources 面板`中还原出原始的 `index.ts` 文件。

# vite构建工具

```

```
