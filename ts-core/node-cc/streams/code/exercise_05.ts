import fs from "node:fs/promises";

// writeMany: 339.198ms
console.time("writeMany");
const fd = await fs.open("./dist/test.txt", "w");
const writableStream = fd.createWriteStream();

for (let i = 0; i <= 1_000_000; i++) {
  writableStream.write(`${i} `);
}
// 1. 关闭流，表示不再写入数据
writableStream.end();

// 2. 等待流完成写入（finish 事件表示数据已全部写入磁盘）
await new Promise((resolve) => writableStream.on("finish", resolve));

// 3. 关闭文件句柄
await fd.close();
console.timeEnd("writeMany");
