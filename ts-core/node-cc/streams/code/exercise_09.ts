import fs from "node:fs/promises";

const fdReader = await fs.open("./dist/test.txt", "r");
const fdWriter = await fs.open("./dist/dest.txt", "w");

const readableStream = fdReader.createReadStream();
const writableStream = fdWriter.createWriteStream();

let buffer = ""; // 暂存区
readableStream.on("data", (chunk) => {
  // 处理
  buffer += chunk.toString("utf-8");

  const _idx = buffer.lastIndexOf(" ");

  const completePart = buffer.substring(0, _idx + 1); // 包括括号
  const nums = completePart.trim().split(" ");

  nums.forEach((num) => {
    if ((Number(num) & 1) === 0) {
      const val = writableStream.write(`${num} `);
      if (!val) {
        // 读取太快，处理不过来，先停止读取
        readableStream.pause();
      }
    }
  });

  // 更新暂存区
  buffer = buffer.substring(_idx + 1);
});

writableStream.on("drain", () => {
  // 写缓冲区已经清理，可以继续写了
  // 恢复生产，继续读获取数据
  readableStream.resume();
});

readableStream.on("end", () => {
  // 处理剩余的buffer
  if (buffer) {
    // 如果 buffer 非空，说明末尾有一个不完整的数字，我们可以处理它
    // 注意：源数据以空格结尾时，buffer 可能是空字符串
    const num = buffer.trim();
    if (num !== "" && (Number(num) & 1) === 0) {
      writableStream.end(`${num} `);
    }
  } else {
    writableStream.end();
  }
});

writableStream.on("finish", async () => {
  console.log("✅ 所有数据已写入磁盘");
  // 此时可以关闭文件描述符
  await fdReader.close();
  await fdWriter.close();
});
