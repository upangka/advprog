// 字符 '深' 的 Unicode 码点（十六进制）
console.log("深".codePointAt(0)?.toString(16)); // 输出: "6df1"

// 从unicode码点获得字符
console.log(String.fromCodePoint(0x6df1)); // 输出: "深"

// '深' 在 UTF-8 编码下的字节（十六进制）
console.log(Buffer.from("深", "utf-8").toString("hex")); // 输出: "e6b7b1"
