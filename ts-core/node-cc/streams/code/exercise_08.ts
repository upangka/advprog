import fs from "node:fs/promises";

const fdReader = await fs.open("./dist/test.txt", "r");
const fdWriter = await fs.open("./dist/dest.txt", "w");

const readableStream = fdReader.createReadStream();
const writableStream = fdWriter.createWriteStream();

let splitNumPart = "";
readableStream.on("data", (chunk) => {
  // 处理
  const nums = chunk.toString("utf-8").split(" ");
  const length = nums.length;

  if (Number(nums[0]) !== Number(nums[1]) - 1) {
    if (splitNumPart) {
      // 接上上次截断的部分
      nums[0] = splitNumPart.trim() + nums[0].trim();
    }
  }

  if (Number(nums[length - 2]) + 1 !== Number(nums[length - 1])) {
    // 不连续有截断
    splitNumPart = nums.pop()!.trim()!;
  }

  nums.forEach((num) => {
    if ((Number(num) & 1) === 0) {
      const val = writableStream.write(`${num} `);
      if (!val) {
        // 读取太快，处理不过来，先停止读取
        readableStream.pause();
      }
    }
  });
});

writableStream.on("drain", () => {
  // 写缓冲区已经清理，可以继续写了
  // 恢复生产，继续读获取数据
  readableStream.resume();
});

readableStream.on("end", () => {
  // 在 readableStream.on("end", ...) 回调执行之前，
  // 所有 data 事件的回调都已经执行完毕。
  // 这是由 Node.js 事件循环的队列顺序保证的。
  // 所以可以放心关闭
  writableStream.end();
});

writableStream.on("finish", async () => {
  console.log("✅ 所有数据已写入磁盘");
  // 此时可以关闭文件描述符
  await fdReader.close();
  await fdWriter.close();
});
