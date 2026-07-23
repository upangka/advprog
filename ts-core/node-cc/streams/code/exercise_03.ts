import fs from "node:fs";
import { Buffer } from "node:buffer";

// writeMany: 1.920s
console.time("writeMany");
fs.open("./dist/test.txt", "w", (err, fd) => {
  for (let i = 0; i <= 1_000_000; i++) {
    fs.writeSync(fd, `${i} `);
  }

  fs.closeSync(fd);
  console.timeEnd("writeMany");
});
