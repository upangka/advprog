import fs from "node:fs/promises";
import { Buffer } from "node:buffer";

// writeMany: 1:30.343 (m:ss.mmm)
console.time("writeMany");
const fd = await fs.open("./dist/test.txt", "w");
for (let i = 0; i <= 1_000_000; i++) {
  await fd.write(`${i} `);
}
await fd.close();
console.timeEnd("writeMany");
