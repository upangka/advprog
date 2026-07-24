# Node启动过程伪代码

```ts
// ============================================================
// Node.js 启动过程（伪代码）
// ============================================================

// 1. 启动进程
function startNodeJS() {
  // 初始化 V8 引擎、libuv、事件循环
  const eventLoop = new EventLoop();
  const taskQueue = new TaskQueue(); // 事件队列（微任务 + 宏任务）

  // 2. 执行所有同步代码（你写的业务逻辑）
  //    这是你的 .ts/.js 文件里的顶层代码
  runYourCode();

  // 3. 同步代码执行完毕后，进入事件循环
  while (true) {
    // 4. 检查事件队列
    if (taskQueue.hasMicroTasks()) {
      // 先执行所有微任务（Promise.then、process.nextTick）
      taskQueue.runAllMicroTasks();
      continue; // 微任务执行完后，重新进入事件循环
    }

    if (taskQueue.hasMacroTasks()) {
      // 执行一个宏任务（setTimeout、setInterval、I/O 回调）
      const task = taskQueue.popMacroTask();
      task();
      continue;
    }

    // 5. 队列为空，检查 libuv 是否还有未完成的任务
    if (libuv.hasPendingTasks()) {
      // 阻塞等待 I/O 完成或定时器触发（进入轮询阶段）
      // 主线程在此处被挂起，由操作系统唤醒
      const completedTask = libuv.waitForCompletion();
      taskQueue.pushMacroTask(completedTask);
      continue;
    }

    // 6. 没有任何任务，退出循环
    break;
  }

  // 7. 进程退出
  process.exit(0);
}
```

**任务 = 回调函数 = 可执行代码块**

| 术语     | 含义                                 | 示例                                                                                       |
| -------- | ------------------------------------ | ------------------------------------------------------------------------------------------ |
| 任务     | 一个等待被执行的工作单元             | `setTimeout(() => {}, 1000)` 中的 `() => {}`                                               |
| 回调函数 | 一个作为参数传递、在将来被调用的函数 | `fs.readFile('file', (err, data) => {})` 中的 `(err, data) => {}`                          |
| 代码块   | 任何可以被执行的 JavaScript 代码     | `Promise.resolve().then(() => { console.log('hi'); })` 中的 `() => { console.log('hi'); }` |
