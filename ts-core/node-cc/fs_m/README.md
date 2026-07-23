# 读取文件

读取一个文件获得的是二进制数据

[exercise_01.ts](./code/exercise_01.ts)

```ts
import fs from "node:fs";
import type { Buffer } from "node:buffer";

const content: Buffer = fs.readFileSync("./messages.txt");
console.log(content); // <Buffer 43 61 6e 20 79 6f 75 20 68 65 61 72 20 6d 65 2e>
console.log(content.toString("utf-8")); // Can you hear me.
```

![](./images/sys_file.png)

# node操作文件的三种方式

1. **Callback API**：最原始的非阻塞 I/O 方式，实现简单，但容易造成“回调地狱”。
2. **Synchronous API**：阻塞式 I/O，代码线性，适合简单脚本，但会阻塞事件循环。
3. **Promises API**：基于 `async/await` 的现代异步方式，代码风格优雅，错误处理统一，是当前推荐的实践。

[exercise_02.ts](./code/exercise_02.ts)

```ts
// ********************Promises API********************************
import * as fs_promises from "node:fs/promises";

(async () => {
  try {
    await fs_promises.copyFile(
      "./resources/poem.txt",
      "./resources/poem_promises.txt",
    );
  } catch (err) {
    console.error(err);
  }
})();

// ********************Callback API********************************
import * as fs from "node:fs";

fs.copyFile("./resources/poem.txt", "./resources/poem_callback.txt", (err) => {
  if (err) {
    console.log(err);
  }
});
// ********************Synchronous API********************************
fs.copyFileSync("./resources/poem.txt", "./resources/poem_sync.txt");
```

# 监听文件变化

[exercise_03.ts](./code/exercise_03.ts)

通过编辑[command.txt](./code/command.txt)程序会一直监听这个文件的变化

```ts
import fs from "node:fs/promises";

async function monitorFileChanges(path: string) {
  const watcher = fs.watch(path);
  // 会在这个循环中监听事件
  for await (const event of watcher) {
    if (event.eventType === "change") {
      console.log(event);
    }
  }

  console.log("File watcher closed.");

  //   保持程序一直运行
  //   await new Promise(() => {});
}

monitorFileChanges("./command.txt");
```

## 小项目：编辑命令文件进行文件操作

[app.ts](./code/f_opt/app.ts),通过在文件[command.txt](./code/f_opt/command.txt)编辑规定的命令，从而创建删除重命名和添加内容

1. 利用了文件的watch功能，同时要处理好防抖
2. 测试环境在vscode中测试正常，使用nvim是有问题，这和我们的程序没有关系，而是编辑器的问题

```ts
import { Buffer } from "node:buffer";
import fs from "node:fs/promises";
import EventEmitter from "node:events";

function debounce(fn: Function, delay: number = 200) {
  let timer: NodeJS.Timeout | null = null;
  return (...args: any[]) => {
    if (timer) clearTimeout(timer);
    timer = setTimeout(() => {
      fn(...args);
      timer = null;
    }, delay);
  };
}

(async () => {
  const TARGET_FILE = "command.txt";
  const eventEmitter = new EventEmitter();

  const CREATE = "Create a file";
  const DELETE = "Delete the file";
  const RENAME = "Rename the file";
  const ADDTOFILE = "Add to file";

  console.log(`
    **********************************************************
        Commands:
        1. ${CREATE}  ex: Create a file <path>
        2. ${DELETE}  ex: Delete the file <path>
        3. ${RENAME}  ex: Rename the file <path> to <newpath>
        4. ${ADDTOFILE} ex: Add to file <path> this content: <content>
    **********************************************************
    `);

  const createFile = async (path: string) => {
    let f: fs.FileHandle | undefined;
    try {
      f = await fs.open(path, "r");
      console.log(`⚠️ 文件 ${path} 已经存在，无需创建`);
    } catch (error: unknown) {
      // Not exist create
      if (error instanceof Error && "code" in error) {
        // ENOENT => Error NO ENTry
        // Unix/Linux 系统中标准的错误码，Node.js 继承了这套体系
        if (error.code == "ENOENT") {
          f = await fs.open(path, "w");
          console.log(`✅ 成功创建文件 ${path}`);
          return;
        }
      }

      // 其他错误重新抛出
      throw error;
    } finally {
      if (f) await f.close();
    }
  };

  const deleteFile = async (path: string) => {
    try {
      await fs.unlink(path);
      console.log(`✅ 文件 ${path} 已成功删除`);
    } catch (error: unknown) {
      if (error instanceof Error && "code" in error) {
        if (error.code === "ENOENT")
          console.log(`⚠️ 文件 ${path} 不存在，无需删除`);
        return;
      }
      console.error(`❌ 删除文件失败:`, error);
      throw error;
    }
  };

  const renameFile = async (oldPath: string, newPath: string) => {
    try {
      await fs.rename(oldPath, newPath);
      console.log(`✅ 成功将 ${oldPath} 重命名为 ${newPath}`);
    } catch (error: unknown) {
      if (error instanceof Error && "code" in error) {
        if (error.code === "ENOENT") {
          console.error(`❌ 源文件或目录不存在: ${oldPath}`);
          return;
        }
      }
      throw error;
    }
  };

  const addContentFile = async (path: string, content: string) => {
    try {
      // 追加内容到文件（如果文件不存在则自动创建）
      await fs.appendFile(path, content, "utf-8");
      console.log(`✅ 内容已追加到 ${path}`);
    } catch (error: unknown) {
      if (error instanceof Error && "code" in error) {
        if (error.code === "ENOENT") {
          // 理论上 appendFile 会自动创建文件，但目录可能不存在
          console.error(`❌ 路径 ${path} 的目录不存在`);
        } else if (error.code === "EACCES") {
          console.error(`❌ 没有权限写入文件 ${path}`);
        } else {
          console.error(`❌ 追加内容失败:`, error);
        }
      } else {
        console.error(`❌ 未知错误:`, error);
      }
      throw error;
    }
  };

  eventEmitter.on("change", async () => {
    // 复用同一个文件句柄可能读取到旧数据（内核缓存问题）。
    // 每次都重新打开，能确保我们读到的是磁盘上最新的内容。
    const fsHandler = await fs.open(TARGET_FILE, "r");
    const f_stat = await fsHandler.stat();
    const buffer = Buffer.alloc(f_stat.size);
    await fsHandler.read(buffer, 0, f_stat.size, 0);
    const content = buffer.toString("utf-8");
    console.log(buffer);
    console.log("=>", content);

    if (content.includes(CREATE)) {
      // Create a file <path>
      const path = content.substring(CREATE.length + 1);
      createFile(path.trim());
    } else if (content.includes(DELETE)) {
      // Delete the file <path>
      const path = content.substring(DELETE.length + 1);
      deleteFile(path.trim());
    } else if (content.includes(RENAME)) {
      // Rename the file <path> to <newPath>
      const _idx = content.indexOf(" to ");
      const oldPath = content.substring(RENAME.length + 1, _idx);
      const newPath = content.substring(_idx + 4);
      renameFile(oldPath.trim(), newPath.trim());
    } else if (content.includes(ADDTOFILE)) {
      // Add to file <path> this content: <content>
      const _idx = content.indexOf("this content:");
      const path = content.substring(ADDTOFILE.length + 1, _idx - 1);
      const txt = content.substring(content.indexOf(":") + 1);
      addContentFile(path.trim(), txt.trimStart());
    } else {
      console.warn("⚠️ 命令不存在");
    }
  });

  // 处理watch的防抖
  const handleChange = debounce(() => eventEmitter.emit("change"));
  for await (const event of fs.watch("./command.txt")) {
    if (event.filename == TARGET_FILE && event.eventType == "change") {
      handleChange();
    }
  }
})();
```

运行效果:

```sh
$ node app.ts

    **********************************************************
        Commands:
        1. Create a file  ex: Create a file <path>
        2. Delete the file  ex: Delete the file <path>
        3. Rename the file  ex: Rename the file <path> to <newpath>
        4. Add to file ex: Add to file <path> this content: <content>
    **********************************************************

<Buffer 41 64 64 20 74 6f 20 66 69 6c 65 20 68 65 6c 6c 6f 2e 74 78 74 20 74 68 69 73 20 63 6f 6e 74 65 6e 74 3a 20 e6 97 a5 e6 9a ae e4 b9 a1 e5 85 b3 e4 bd ... 37 more bytes>
=> Add to file hello.txt this content: 日暮乡关何处是？烟波江上使人愁。!!!
✅ 内容已追加到 hello.txt
```
