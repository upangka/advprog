好的，我按李宏毅老师这份 `seq2seq_v9.pdf` 课件的**讲授顺序**，把它拆成一个个循序渐进的 Unit。你可以把它当作“听课路线图”，每完成一个 Unit 再进下一个，不容易乱。

我拆分的依据是：**先讲 Seq2seq 是什么 → 再讲 Encoder → 再讲 Decoder（AT/NAT）→ 再讲 Encoder-Decoder 的连接 → 训练 → 测试技巧 → 总结**。这份课件本质上是 **Transformer 的入门讲解**，但李老师是从 seq2seq 的角度切入的。

---

## Unit 0：先导——Seq2seq 是什么

**对应课件**：Page 2–Page 15

**核心问题**：

- 什么是 Sequence-to-sequence？
- 为什么输出长度由模型决定？
- Seq2seq 能用在哪些任务上？

**你要掌握的内容**：

1. **Seq2seq 的定义**（Page 2）
   - 输入一个序列，输出一个序列
   - 输出长度不是固定的，而是由模型自己决定
   - 例子：语音识别、机器翻译、语音翻译

2. **Seq2seq 的广泛应用**（Page 3–Page 14）
   - 闽南语语音识别/翻译（Page 3–5）
   - TTS 语音合成（Page 6）
   - Chatbot（Page 7）
   - QA（Page 8）
   - 句法分析 Syntactic Parsing（Page 10–12）
   - 多标签分类 Multi-label Classification（Page 13）
   - 目标检测 Object Detection（Page 14，DETR）

3. **Seq2seq 的一般结构**（Page 15）
   - Encoder → Decoder
   - 输入序列进 Encoder，Decoder 输出序列
   - 这一页右下角就是 **Transformer 的完整结构图**，是本课件后面所有内容的“总图”

**学习建议**：

- 这一 Unit 不用死记每个应用，重点是建立“**Seq2seq 是一个通用框架**”的感觉。
- 记住 Page 15 那张图：左边是 Encoder，右边是 Decoder，中间有连接。

---

## Unit 1：Encoder

**对应课件**：Page 16–Page 21

**核心问题**：

- Encoder 做什么？
- Encoder 内部有哪些组件？
- 为什么需要 Self-Attention、Residual、Layer Norm？

**你要掌握的内容**：

1. **Encoder 的基本作用**（Page 16–17）
   - 输入序列 \(x^1, x^2, x^3, x^4\)
   - 输出隐藏表示 \(h^1, h^2, h^3, h^4\)
   - 可以用 RNN 或 CNN 实现，但 Transformer 用的是 Self-Attention

2. **Transformer Encoder 的结构**（Page 17–18）
   - 输入先做 Input Embedding
   - 加上 Positional Encoding
   - 进入多个 Block（N×）
   - 每个 Block 包含：
     - Multi-Head Attention
     - Add & Norm
     - Feed Forward
     - Add & Norm

3. **Self-Attention 的细节**（Page 18）
   - 输入 \(x^i\) 经过 Self-attention 得到输出
   - 每个输出都看过所有输入

4. **Residual + Layer Norm**（Page 19–20）
   - Residual：\(a + b\)
   - Layer Norm：对每个样本自己做归一化，公式 \(x_i' = \frac{x_i - m}{\sigma}\)
   - 注意：Layer Norm 和 Batch Norm 不一样

5. **Pre-LN vs Post-LN**（Page 21）
   - 原始 Transformer 是 Post-LN
   - 后来有 Pre-LN 变体，训练更稳定
   - 这一页是“to learn more”，了解即可

**学习建议**：

- 这一 Unit 是重点，务必搞懂 **Self-Attention + Residual + Layer Norm** 这三件套。
- 如果 Self-Attention 还不熟，先回去补 L08。

---

## Unit 2：Decoder——Autoregressive (AT)

**对应课件**：Page 22–Page 34

**核心问题**：

- Decoder 怎么生成序列？
- 什么是 Autoregressive？
- 为什么需要 Masked Self-Attention？
- 怎么决定输出长度？

**你要掌握的内容**：

1. **Decoder 的基本作用**（Page 22–23）
   - 输入 Encoder 的输出
   - 输出 output sequence

2. **Autoregressive (AT) 的工作方式**（Page 24–25）
   - 先输入 START token
   - 输出第一个词
   - 把第一个词作为下一步输入
   - 一步一步生成，直到输出 END

3. **Masked Self-Attention**（Page 26–29）
   - Decoder 里的 Self-Attention 是 Masked 的
   - 每个位置只能看到自己和自己之前的输入
   - 不能看到未来的词
   - 原因：Decoder 是 autoregressive 的，生成第 \(t\) 个词时，第 \(t+1\) 个词还不存在

4. **输出长度怎么决定**（Page 30–34）
   - 不知道输出多长
   - 加一个 “Stop Token”（END）
   - 模型输出 END 就停止
   - 例子：推文接龙（Page 32）

**学习建议**：

- 这一 Unit 的关键是理解 **Masked Self-Attention 为什么必须存在**。
- 可以自己画一遍：输入 START → 输出“机” → 输入“机” → 输出“器” → … → 输出 END。

---

## Unit 3：Decoder——Non-Autoregressive (NAT)

**对应课件**：Page 35–Page 37

**核心问题**：

- 什么是 NAT？
- NAT 和 AT 有什么区别？
- NAT 的优缺点是什么？

**你要掌握的内容**：

1. **NAT 的工作方式**（Page 35–36）
   - 一次性输入多个 START
   - 一次性输出整个序列
   - 并行生成，速度快

2. **NAT 怎么决定输出长度**（Page 36）
   - 方法一：另训练一个 predictor 预测长度
   - 方法二：输出一个很长的序列，忽略 END 之后的 token

3. **AT vs NAT**（Page 36）
   - NAT 优点：并行、生成更稳定（如 TTS）
   - NAT 缺点：通常比 AT 差
   - 原因：Multi-modality 问题

**学习建议**：

- 这一 Unit 不是重点，了解 NAT 的存在和基本思想即可。
- 2021 版课程里，Transformer 主线还是 AT Decoder。

---

## Unit 4：Encoder-Decoder 怎么连接——Cross Attention

**对应课件**：Page 38–Page 43

**核心问题**：

- Encoder 和 Decoder 之间怎么传递信息？
- 什么是 Cross Attention？
- Cross Attention 的 Q、K、V 分别来自哪里？

**你要掌握的内容**：

1. **Encoder-Decoder 结构**（Page 38）
   - Encoder 处理输入序列
   - Decoder 生成输出序列

2. **Cross Attention**（Page 39–41）
   - Decoder 里有一个 Multi-Head Attention 是 Cross Attention
   - **Q 来自 Decoder**
   - **K、V 来自 Encoder**
   - 这样 Decoder 每一步都能“看”到 Encoder 的全部输出

3. **Cross Attention 的具体计算**（Page 40–41）
   - Encoder 输出 \(a^1, a^2, a^3\)
   - 分别算出 \(k^1, k^2, k^3\) 和 \(v^1, v^2, v^3\)
   - Decoder 的 hidden state 算出 \(q\)
   - \(q\) 和所有 \(k\) 做 attention，再对 \(v\) 加权求和

4. **更复杂的连接方式**（Page 43）
   - 原始 Transformer 是 Encoder 最后一层连 Decoder 每一层
   - 后来有各种变体：Granularity Consistent、Parallel、Fine-Grained、Full Matching、Adaptive Matching
   - 这一页是“to learn more”，了解即可

**学习建议**：

- 这一 Unit 的关键是记住：**Cross Attention 的 Q 来自 Decoder，K 和 V 来自 Encoder**。
- 可以和 Self-Attention 对比：Self-Attention 的 Q、K、V 都来自同一个地方。

---

## Unit 5：Training——Teacher Forcing

**对应课件**：Page 44–Page 46

**核心问题**：

- Transformer 怎么训练？
- 什么是 Teacher Forcing？
- 训练时 Decoder 的输入是什么？

**你要掌握的内容**：

1. **训练目标**（Page 45）
   - Decoder 输出 distribution
   - 和 Ground Truth 做 cross entropy
   - 最小化 cross entropy

2. **Teacher Forcing**（Page 46）
   - 训练时，Decoder 的输入不是自己上一步的输出
   - 而是 **Ground Truth**
   - 例如：输入 `<BOS>`，目标输出“机”；下一步输入“机”，目标输出“器”；以此类推
   - 这样训练更稳定

**学习建议**：

- 这一 Unit 的关键是理解 **训练时用 Ground Truth，测试时用自己生成的 token**。
- 这个差异会导致后面 Page 56 的 mismatch 问题。

---

## Unit 6：Tips——训练和测试的实用技巧

**对应课件**：Page 47–Page 58

**核心问题**：

- Copy Mechanism 是什么？
- Guided Attention 是什么？
- Beam Search 是什么？
- 为什么需要 Sampling？
- 怎么优化 Evaluation Metrics？
- 什么是 Scheduled Sampling？

**你要掌握的内容**：

1. **Copy Mechanism**（Page 48–50）
   - 有些任务需要直接复制输入中的词
   - 例如：机器翻译中的人名、聊天机器人中的名字
   - 代表工作：Pointer Network、Copy Mechanism

2. **Guided Attention**（Page 51–52）
   - 某些任务输入输出是单调对齐的
   - 例如：语音识别、TTS
   - 希望 Attention 按照顺序移动
   - 如果 Attention 乱跳，就会出错

3. **Beam Search**（Page 53）
   - Greedy Decoding 只选当前概率最大的
   - Beam Search 保留多个候选路径
   - 但 Beam Search 不一定是“最好”的

4. **Sampling**（Page 54）
   - 有些任务需要随机性
   - 例如：文本生成
   - Pure Sampling 可能生成很离谱的内容
   - 需要接受“不完美”

5. **Optimizing Evaluation Metrics**（Page 55）
   - 训练时用 cross entropy，测试时用 BLEU
   - 两者不一致
   - 不知道怎么优化时，可以用 Reinforcement Learning

6. **Mismatch 问题**（Page 56）
   - 训练时看 Ground Truth
   - 测试时看自己生成的 token
   - 一旦前面生成错，后面会越来越错

7. **Scheduled Sampling**（Page 57–58）
   - 训练时，有时候用 Ground Truth，有时候用模型自己的输出
   - 缓解 mismatch 问题

**学习建议**：

- 这一 Unit 是“技巧合集”，不需要一次全懂。
- 优先掌握：**Copy Mechanism、Beam Search、Mismatch 问题、Scheduled Sampling**。

---

## Unit 7：总结——Transformer 全貌

**对应课件**：Page 59–Page 60

**核心问题**：

- Transformer 的完整结构是什么？
- Encoder 和 Decoder 各有什么组件？

**你要掌握的内容**：

1. **完整结构图**（Page 59）
   - 左边 Encoder：
     - Input Embedding + Positional Encoding
     - N× Block（Multi-Head Attention + Add & Norm + Feed Forward + Add & Norm）
   - 右边 Decoder：
     - Output Embedding + Positional Encoding
     - N× Block（Masked Multi-Head Attention + Add & Norm + Cross Attention + Add & Norm + Feed Forward + Add & Norm）
   - 最后 Linear + Softmax 输出概率

2. **回顾整个课件**（Page 60）
   - Seq2seq → Encoder → Decoder → Cross Attention → Training → Tips → 总结

**学习建议**：

- 这一 Unit 是收尾，把前面所有内容串起来。
- 可以自己默画一遍 Transformer 结构图，能画出来就说明掌握了。

---

## 建议的学习顺序

如果你要循序渐进地跟上，我建议按这个顺序：

1. **Unit 0**：Seq2seq 是什么（Page 2–15）
2. **Unit 1**：Encoder（Page 16–21）
3. **Unit 2**：Decoder AT（Page 22–34）
4. **Unit 4**：Cross Attention（Page 38–43）
5. **Unit 5**：Training（Page 44–46）
6. **Unit 6**：Tips（Page 47–58）
7. **Unit 3**：NAT（Page 35–37，可以最后看）
8. **Unit 7**：总结（Page 59–60）

其中 **Unit 1、Unit 2、Unit 4** 是 Transformer 的核心，必须扎实。

---

你现在可以告诉我：  
**你想从哪个 Unit 开始？**  
或者你现在看到哪一页卡住了，我按这个 Unit 拆分给你讲。

# Resources

[deepseek学习笔记](https://chat.deepseek.com/a/chat/s/83625c30-7b73-459c-a67a-abba7c8d1e9b)
