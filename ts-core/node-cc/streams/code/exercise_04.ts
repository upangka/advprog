import fs from "node:fs";
import { Buffer } from "node:buffer";

// writeMany: 1.895s
console.time("writeMany");
fs.open("./dist/test.txt", "w", (err, fd) => {
  for (let i = 0; i <= 1_000_000; i++) {
    fs.write(fd, `${i} `, (err) => {});
  }
  console.timeEnd("writeMany");
});
