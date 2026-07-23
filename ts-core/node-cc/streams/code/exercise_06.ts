/******************************************************
 *  Node.js Stream 的背压机制在底层是按
 * “缓冲区满 → 写入磁盘 → 清空 → 触发 drain” 这个循环工作
 ******************************************************/

import fs from "node:fs/promises";

console.time("writeMany");
const fd = await fs.open("./dist/test.txt", "w");
const writableStream = fd.createWriteStream();

// 查看水位线
const highWaterMark = writableStream.writableHighWaterMark;
console.log(highWaterMark); // 65535

const TARGET = 1_000_000;

let _idx = 0,
  count = 0;
const writeMany = () => {
  while (_idx < TARGET) {
    const val = writableStream.write(`${_idx} `);
    _idx++;
    if (!val) {
      // 此时到达水位线，消费不过来了，先停止生产
      break;
    }
  }

  if (_idx === TARGET) {
    console.log("准备关闭stream");
    writableStream.end();
  }
};

writableStream.on("drain", () => {
  // 缓存区已经清空，继续恢复生产者
  writeMany();
  count++;
});

writableStream.on("finish", async () => {
  console.log("已经全部写入", `其中缓存区一共清理${count}次`);
  console.timeEnd("writeMany");

  const size = (await fd.stat()).size;
  console.log("生成文件大小", size, "bytes");
  // 计算应该与count一样
  console.log(Math.floor(size / highWaterMark) === count);
});

writeMany();
