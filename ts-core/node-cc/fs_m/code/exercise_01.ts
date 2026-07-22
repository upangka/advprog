import fs from "node:fs";
import type { Buffer } from "node:buffer";

const content: Buffer = fs.readFileSync("./messages.txt");
console.log(content); // <Buffer 43 61 6e 20 79 6f 75 20 68 65 61 72 20 6d 65 2e>
console.log(content.toString("utf-8")); // Can you hear me.
