好的，按你的偏好，我把这一讲整理成 **Unit 大纲**。每个 Unit 都有明确的学习目标、核心内容和自测问题，你可以一个 Unit 一个 Unit 地过。

---

# Self-Supervised Learning 单元大纲

**前置要求**：Transformer Encoder、Self-Attention、Word Embedding

---

## Unit 1：为什么需要自监督学习

**对应课件**：P1–P9

**学习目标**：

- 理解「标注数据不够」这个核心痛点
- 知道 Self-Supervised Learning 的基本思路：先预训练，再 fine-tune
- 认识 BERT 家族的主要成员

**核心内容**：

- 芝麻街梗图：ELMo、BERT、ERNIE、Big Bird 的角色对应
- 模型规模演变：ELMo (94M) → BERT (340M) → GPT-2 (1542M) → GPT-3 (175B) → Switch Transformer (1.6T)
- 预训练 + fine-tune 的两阶段范式

**自测问题**：

1. 为什么不能直接拿标注数据训练一个大模型？
2. Self-Supervised 的「监督信号」从哪里来？

---

## Unit 2：BERT 的预训练任务

**对应课件**：P11–P13

**学习目标**：

- 掌握 Masked Token Prediction 的完整流程
- 理解 Next Sentence Prediction 的设计和它的缺陷

**核心内容**：

- **Masked Token Prediction**：
  - 随机遮住一些 token（用 `MASK` 或随机替换）
  - BERT 输出 → Linear → Softmax → 和 Ground Truth 算交叉熵
  - 例子：`台 [MASK] 大 學` → 预测 `灣`
- **Next Sentence Prediction**：
  - 输入格式：`[CLS]` + 句子1 + `[SEP]` + 句子2
  - 输出 Yes/No
  - 课件明确标注 **"This approach is not helpful"**
  - RoBERTa 去掉 NSP，ALBERT 改用 SOP

**自测问题**：

1. Masked Token Prediction 为什么要随机遮，而不是固定遮？
2. NSP 为什么效果不好？SOP 和 NSP 的区别是什么？

---

## Unit 3：BERT 的四种使用方式

**对应课件**：P14–P25

**学习目标**：

- 掌握 BERT 在四类下游任务上的输入输出结构
- 理解 `[CLS]` 和 `[SEP]` 的作用
- 能区分「BERT 本体 pre-train 初始化」和「任务头 random 初始化」

**核心内容**：

| Case   | 输入                | 输出            | 例子        |
| ------ | ------------------- | --------------- | ----------- |
| Case 1 | 一个序列            | 一个类别        | 情感分析    |
| Case 2 | 一个序列            | 等长序列        | POS tagging |
| Case 3 | 两个序列            | 一个类别        | NLI         |
| Case 4 | question + document | (s, e) 两个整数 | 抽取式 QA   |

**重点细节**：

- Case 1：`[CLS]` 的输出接 Linear 做分类
- Case 4：两个 random initialized 向量分别和 document 表示做 inner product，再 softmax 得到 start/end
- P18 的 training loss 曲线：pre-train 初始化 vs random 初始化

**自测问题**：

1. `[CLS]` 和 `[SEP]` 分别在什么位置、起什么作用？
2. Case 4 里为什么需要两个独立的向量？能不能共用一个？
3. 为什么任务头要 random 初始化，而 BERT 本体用 pre-train 初始化？

---

## Unit 4：BERT 家族与变体

**对应课件**：P26–P31

**学习目标**：

- 认识 BERT 之后的主要改进模型
- 理解 seq2seq 预训练的思路

**核心内容**：

- **ALBERT**：Google 版，GLUE 分数高于 BERT-base
- **训练成本**：3 billion 词，8 天 TPU v3
- **BERT Embryology**：BERT 在预训练过程中什么时候学会 POS、句法、语义
- **MASS / BART**：
  - BART 的破坏方式：text infilling、delete、permutation、rotation
  - 目标：reconstruct the input
- **T5**：
  - 一切任务都变成 text-to-text
  - 数据集：C4（Colossal Clean Crawled Corpus）

**自测问题**：

1. BART 和 BERT 的预训练目标有什么本质区别？
2. T5 的「text-to-text」统一框架解决了什么问题？

---

## Unit 5：为什么 BERT 有效

**对应课件**：P32–P38

**学习目标**：

- 从 embedding 角度理解 BERT 学到了什么
- 理解 context-dependent 表示
- 知道 BERT 可以跨领域迁移

**核心内容**：

- **Embedding 相似性**：意思相近的 token，embedding 也相近
- **Context 被考虑**：
  - 「喝蘋果汁」里的「果」 vs 「蘋果電腦」里的「果」
  - 同一个字在不同上下文里 embedding 不同
- **Firth 名言**：_"You shall know a word by the company it keeps"_
- **CBOW 对比**：BERT 不是简单的 context 平均，而是有 attention 机制
- **跨领域迁移**：protein、DNA、music 分类
- **P37 的 DNA 例子**：A/T/C/G 对应 we/you/he/she

**自测问题**：

1. BERT 的 embedding 和 Word2Vec 的 embedding 有什么本质区别？
2. 为什么 BERT 能用在 DNA 序列上？它学到的到底是什么？

---

## Unit 6：Multi-lingual BERT

**对应课件**：P40–P49

**学习目标**：

- 理解多语言 BERT 的训练方式和 zero-shot 能力
- 知道跨语言对齐和训练数据量的关系
- 理解「语言信息」藏在 embedding 的哪里

**核心内容**：

- **训练方式**：多种语言一起训练同一个 BERT
- **Zero-shot Reading Comprehension**：
  - 英文 QA 训练，中文 QA 测试
  - P42 表格：F1 从 78.8 到 90.1
- **Cross-lingual Alignment**：
  - MRR 指标
  - 训练数据量越大，对齐越好（200k vs 1000k sentences）
- **语言信息藏在哪里**：
  - P48–P49：embedding 里确实有语言信息
  - 可以做无监督 token-level translation

**自测问题**：

1. Multi-BERT 为什么能做到 zero-shot cross-lingual？
2. MRR 衡量的是什么？为什么训练数据量对对齐这么重要？
3. 「语言信息」和「语义信息」在 embedding 里是怎么共存的？

---

## Unit 7：GPT 系列与自监督

**对应课件**：P50–P56

**学习目标**：

- 理解 GPT 的预训练目标和 BERT 的区别
- 知道 GPT 为什么能做 generation
- 理解 In-context Learning 和 Few-shot 的概念

**核心内容**：

- **Predict Next Token**：只能看到左边（单向）
- **和 BERT 的核心区别**：
  - BERT：双向，Masked LM，适合理解任务
  - GPT：单向，Next Token Prediction，适合生成任务
- **GPT-2 生成例子**：独角兽文章
- **In-context Learning**：P54 的翻译例子
- **Few Shot / One Shot / Zero Shot**：P55 随模型规模增长的效果

**自测问题**：

1. 为什么 BERT 不能做 generation，而 GPT 可以？
2. In-context Learning 和 fine-tune 的区别是什么？
3. 为什么 Few-shot 的效果随模型规模增长而提升？

---

## Unit 8：Beyond Text

**对应课件**：P57–P62

**学习目标**：

- 知道自监督学习不限于文本
- 认识图像和语音领域的主要自监督方法

**核心内容**：

- **图像**：
  - SimCLR：Maximize agreement
  - BYOL：Bootstrap your own latent
- **语音**：
  - Speech GLUE / SUPERB
  - 10+ downstream tasks
  - Toolkit：s3prl
- **三领域对比**：NLP、Speech、CV

**自测问题**：

1. SimCLR 和 BYOL 的核心区别是什么？
2. 语音自监督和文本自监督的难点有什么不同？

---

## 学习路径建议

```
Unit 1 → Unit 2 → Unit 3 → Unit 4 → Unit 5 → Unit 6 → Unit 7 → Unit 8
  │        │        │        │        │        │        │        │
  │        │        │        │        │        │        │        └─ 扩展视野
  │        │        │        │        │        │        └─ GPT 对比
  │        │        │        │        │        └─ 多语言
  │        │        │        │        └─ 理论解释
  │        │        │        └─ 家族变体
  │        │        └─ 实战应用（最重要）
  │        └─ 预训练任务
  └─ 动机
```

**重点 Unit**：Unit 3（四种使用方式）和 Unit 5（为什么有效）是这一讲的核心，其他 Unit 是围绕它们展开的。

---

你现在想从哪个 Unit 开始？告诉我，我就按那个 Unit 的粒度给你讲。
