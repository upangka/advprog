# 古典概型

**古典概型 = 有限样本空间 + 等可能结果 → 概率就是「数数相除」**

「数数法」解释一个问题：

> 抛 3 枚硬币，3 次都是正面的概率是多少？

$P(HHH) = \frac{1}{8} = 0.125$。

![alt text](./images/coin.png)

## 数学定义

两个关键术语

| 术语     | 英文         | 含义                              | 本例             |
| -------- | ------------ | --------------------------------- | ---------------- |
| 样本空间 | Sample Space | 所有可能结果的集合，记作 $\Omega$ | 10 个孩子        |
| 事件     | Event        | 你关心的结果子集，记作 $A$        | 3 个踢足球的孩子 |

$$
P(A) = \frac{|A|}{|\Omega|}
$$

其中 $|A|$ 表示集合 $A$ 的元素个数（基数）。

**实验（Experiment）**：任何产生不确定结果的过程。抛硬币、掷骰子、抽孩子都是实验。

![alt text](./images/soccer.png)

# Complement of Probability补集

**complement** /ˈkɒmplɪmənt/ 补足物；补集；补角。概率课程语境里，complement 特指**补集**：事件 $A$ 不发生的那部分，记作 $A'$ 或 $A^c$。它和 $A$ 互斥且完备，两者合起来就是整个样本空间 $\Omega$，所以满足 $P(A') = 1 - P(A)$。

`补集 = 1 − 事件概率`。它不需要任何前提，因为「发生」和「不发生」天然就把 100% 分完了。

$A$ 和 $A'$ 是**互斥**（disjoint）且**完备**（exhaustive）的：

- 互斥：$A \cap A' = \varnothing$，不可能同时发生
- 完备：$A \cup A' = \Omega$，必有一个发生

所以 $P(A) + P(A') = 1$，移项就得到补集规则。

![alt text](./images/complement.png)

# Sum of Probabilities

## Disjoint Events

两个事件「或」的概率，什么时候可以直接相加？**当它们互斥（disjoint）时**。直观感受就是**互斥 = 不重叠**

求和规则不是无条件的，它的前提是「互斥」。

$$
P(A \cup B) = P(A) + P(B) \quad (\text{仅当 } A \cap B = \varnothing)
$$

![alt text](./images/disjoint.png)

**ML 里的求和规则**：

- 多分类 softmax：每个类别的概率加起来等于 1，因为类别互斥
- 词袋模型：每个词出现与否，若假设互斥（朴素贝叶斯的前提），概率可加
- 决策树的叶节点：每个叶节点对应互斥的区域

## Joint Events(容斥原理)

上个Disjoint Events的概率求和只在**互斥**时成立。这里把前提拿掉，处理**可以同时发生**的事件。

$$
P(A \cup B) = P(A) + P(B) - P(A \cap B)
$$

多出来的 $P(A \cap B)$ 就是重叠（overlap）部分，因为直接相加会把它数两次。

核心就是 **容斥原理（inclusion-exclusion principle） = 先把各块加起来，再把重复数的减掉**。
「容」是容纳（加），「斥」是排斥（减），合起来就是「去重」。

> 用大白话讲：
> 你要数「A 或 B」有多少，先把 A 和 B 各自的数量加起来。
> 但如果你直接加，同时属于 A 和 B 的那部分会被数两次。
> 所以要把多算的那一次减掉。

![alt text](./images/overlap.png)

---

## Disjoint与Joint的直观区别

![alt text](./images/disjoint-and-joint.png)
