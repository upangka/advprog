// 字符H二进制的表示形式
let H_binary = 0b01001000;
console.log(H_binary); // 72
let H_hex = 0x48;
console.log(H_hex); // 72

// 对应为ascii码表 man ascii
const H_char = String.fromCharCode(H_binary);
console.log(H_char); // H

// 获得'H'的ascii码
const H_ascii = "H".charCodeAt(0);
console.log(H_ascii); // 72
