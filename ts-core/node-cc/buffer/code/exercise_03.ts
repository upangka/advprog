import { Buffer } from "node:buffer";

console.log(Buffer.from("Hi,深圳", "utf-8")); // 输出: <Buffer 48 69 2c e6 b7 b1 e5 9c b3>

// 分配9字节的缓冲区,其中字节存储的是utf-8编码的字符串"Hi,深圳"
const buf = Buffer.alloc(9);

buf[0] = 0x48; // 'H'
buf[1] = 0x69; // 'i'
buf[2] = 0x2c; // ,
// 深
buf[3] = 0xe6; //
buf[4] = 0xb7; //
buf[5] = 0xb1; //
// 圳
buf[6] = 0xe5; //
buf[7] = 0x9c; //
buf[8] = 0xb3; //

console.log(buf); // 输出: <Buffer 48 69 2c e6 b7 b1 e5 9c b3>
console.log(buf.toString("utf-8")); // 输出: "Hi,深圳"
