import fs from "node:fs/promises";
import { Buffer } from "node:buffer";

const srcFile = await fs.open("./dist/test.txt", "r");
const desFile = await fs.open("./dist/copy.txt", "w");
let bytesRead = -1;
while (bytesRead !== 0) {
  // 16384 也是默认值
  const result = await srcFile.read({ length: 16384 });
  bytesRead = result.bytesRead;

  if (bytesRead == 0) {
    // 没有数据直接退出
    break;
  } else if (bytesRead < 16384) {
    // 处理未装满的
    const newBuffer = Buffer.alloc(bytesRead);
    result.buffer.copy(newBuffer, 0, 0, bytesRead);
    await desFile.write(newBuffer);
  } else {
    await desFile.write(result.buffer);
  }
}

const srcStat = await srcFile.stat();
const desStat = await desFile.stat();
console.log(srcStat.size, desStat.size);

await desFile.close();
await srcFile.close();
