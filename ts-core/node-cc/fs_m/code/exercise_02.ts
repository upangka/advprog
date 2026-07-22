// ********************Promises API********************************
import * as fs_promises from "node:fs/promises";

(async () => {
  try {
    await fs_promises.copyFile(
      "./resources/poem.txt",
      "./resources/poem_promises.txt",
    );
  } catch (err) {
    console.error(err);
  }
})();

// ********************Callback API********************************
import * as fs from "node:fs";

fs.copyFile("./resources/poem.txt", "./resources/poem_callback.txt", (err) => {
  if (err) {
    console.log(err);
  }
});
// ********************Synchronous API********************************
fs.copyFileSync("./resources/poem.txt", "./resources/poem_sync.txt");
