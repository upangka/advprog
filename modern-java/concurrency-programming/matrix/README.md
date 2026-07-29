# 矩阵计算

# 运行

使用`jbang`直接运行入口[main.java](./main.java)

```sh
jbang main.java
```

运行结果

```sh
SerialMultiplier 耗时: 89434ms(89.43s)
ParallelIndividualMultiplier 耗时: 660412ms(660.41s)
ParallelRowMultiplier 耗时: 25962ms(25.96s)
ParallelGroupMultiplier 耗时: 26244ms(26.24s)
```

1. `SerialMultiplier`单线程运行
2. `ParallelIndividualMultiplier` 每个单元开一个线程 （最耗时）
3. `ParallelRowMultiplier`每行开一个线程处理，以处理器数量为一个轮次，等待这批次执行完成，再开下一轮
4. `ParallelGroupMultiplier`将所有行分配到处理器数量的线程。

# 小结

从串行版本[SerialMultiplier.java](SerialMultiplier.java)计算，到任务拆分独立，交给每个线程去执行。任务拆分从细到合理的广度。独立单元[ParallelIndividualMultiplier.java](./ParallelIndividualMultiplier.java)独立线程,到独立行[ParallelRowMultiplier.java](./ParallelRowMultiplier.java)独立线程，最后到用线程类分配行[ParallelGroupMultiplier.java](./ParallelGroupMultiplier.java)

```sh
 matrix
    ├── main.java   入口
    ├── MatrixGenerator.java 生成矩阵
    ├── Multiplier.java 统一接口

    ├── SerialMultiplier.java  串行

    ├── IndividualMultiplierTask.java 单元任务
    ├── ParallelIndividualMultiplier.java

    ├── RowMultiplierTask.java  行任务
    ├── ParallelRowMultiplier.java

    线程数量分配任务
    ├── GroupMultiplierTask.java
    ├── ParallelGroupMultiplier.java


```
