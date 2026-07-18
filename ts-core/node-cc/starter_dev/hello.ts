// const http = require("node:http");
// const fs = require("node:fs");
import http from "node:http";
import fs from "node:fs";

const server = http.createServer();

server.on("request", (request, response) => {
  response.setHeader("Content-Type", "text/plain");
  const result = fs.readFileSync("msg.txt");
  response.write(result);
  response.end();
});

server.listen(4080, "localhost", () => {
  // console.log(`Server has started on: ${server.address()}`);
  // console.log(`Server has started on: ${JSON.stringify(server.address())}`);
  console.log(`Server has started on:`, server.address());
});
