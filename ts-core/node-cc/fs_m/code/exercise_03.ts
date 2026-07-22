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
