import { Buffer } from "node:buffer";
import fs from "node:fs/promises";
import EventEmitter from "node:events";

(async () => {
  const TARGET_FILE = "command.txt";
  const eventEmitter = new EventEmitter();
  const fsHandler = await fs.open(TARGET_FILE, "r");

  const CREATE = "Create a file";

  const createFile = async (path: string) => {
    let f: fs.FileHandle | undefined;
    try {
      f = await fs.open(path, "r");
      console.log(`The File ${path} is exists`);
    } catch (error: unknown) {
      // Not exist create
      if (error instanceof Error && "code" in error) {
        // ENOENT => Error NO ENTry
        // Unix/Linux 系统中标准的错误码，Node.js 继承了这套体系
        if (error.code == "ENOENT") {
          f = await fs.open(path, "w");
          console.log(`Success create File ${path}`);
          return;
        }
      }

      // 其他错误重新抛出
      throw error;
    } finally {
      if (f) await f.close();
    }
  };

  eventEmitter.on("change", async () => {
    const f_stat = await fsHandler.stat();
    const buffer = Buffer.alloc(f_stat.size);
    fsHandler.read(buffer, 0, f_stat.size, 0);
    // console.log(buffer);
    // console.log(buffer.toString("utf-8"));
    const content = buffer.toString("utf-8");

    // Create a file
    // Create a file path
    if (content.includes(CREATE)) {
      const path = content.substring(CREATE.length + 1);
      createFile(path);
    }
  });

  for await (const event of fs.watch("./")) {
    if (event.filename === TARGET_FILE && event.eventType === "change") {
      eventEmitter.emit("change");
    }
  }
})();
