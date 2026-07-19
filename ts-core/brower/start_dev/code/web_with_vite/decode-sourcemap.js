const base64String = `eyJtYXBwaW5ncyI6IkFBQUEsTUFBTSxPQUFPLFFBQWdCO0NBQzNCLFFBQVEsSUFBSSxHQUFHO0FBQ2pCO0FBRUEsSUFBSSxzQ0FBc0MiLCJuYW1lcyI6W10sInNvdXJjZXMiOlsibWFpbi50cyJdLCJ2ZXJzaW9uIjozLCJzb3VyY2VzQ29udGVudCI6WyJjb25zdCBydW4gPSAobXNnOiBzdHJpbmcpID0+IHtcbiAgY29uc29sZS5sb2cobXNnKTtcbn07XG5cbnJ1bihcIkhlbGxvIFdvcmxkIGZyb20gQnJvd3Nlci5UUy13b3JrIDotKVwiKTtcbiJdfQ==`;

// 步骤1: base64 解码，得到 JSON 字符串
const decodedJsonString = atob(base64String);
console.log("📄 解码后的 JSON 字符串:");
console.log(decodedJsonString);
console.log("\n---\n");

// 步骤2: 将 JSON 字符串解析为 JavaScript 对象
const sourceMapObject = JSON.parse(decodedJsonString);
console.log("📦 解析后的 Source Map 对象:");
console.log(JSON.stringify(sourceMapObject, null, 2));
console.log("\n---\n");

// 步骤3: 提取原始源代码
console.log("📝 原始源代码 (sourcesContent):");
console.log(sourceMapObject.sourcesContent[0]);
console.log("\n---\n");

// 步骤4: 查看其他字段
console.log("📂 源文件名 (sources):");
console.log(sourceMapObject.sources);
