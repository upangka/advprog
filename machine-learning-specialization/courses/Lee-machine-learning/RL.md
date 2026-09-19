关键点：$e$ 本身没有"鼓励"或"打压"的含义，它只是一个差距度量。是 $L$ 里 $e$ 前面的正负号，决定了是鼓励还是打压。

## 🧭 学习大纲：Deep Reinforcement Learning

### Unit 1：什么是强化学习（RL）？

**目标**：理解 RL 与监督学习的区别，掌握 RL 的三步骤框架。

- **1.1 从监督学习到 RL**
  - 监督学习需要人工标签，RL 只需要奖励（reward）
  - 有些任务难以标注，但机器可以知道结果好坏
- **1.2 机器学习三步骤在 RL 中的体现**
  - Step 1：定义函数（Actor / Policy Network）
  - Step 2：定义损失（Loss）
  - Step 3：优化（Optimization）
- **1.3 示例：玩游戏（Space Invader）与下围棋（AlphaGo）**
  - Observation → Actor → Action → Reward → Environment
  - 目标：找到最大化期望奖励的 Actor

---

### Unit 2：Policy Gradient（策略梯度）

**目标**：掌握如何通过梯度上升训练 Actor，理解 Version 0 ~ 3 的演进。

- **2.1 如何控制 Actor**
  - 输入 observation，输出每个 action 的分数
  - 通过交叉熵定义 Loss，控制 Actor 采取或不采取某动作
- **2.2 Version 0：短视版**
  - 用 immediate reward 作为权重
  - 问题：奖励延迟（reward delay），只看到眼前利益
- **2.3 Version 1：考虑累积奖励**
  - 用 \( G*t = \sum*{n=t}^N r_n \) 作为权重
- **2.4 Version 2：引入折扣因子（Discount Factor）**
  - \( G*t' = \sum*{n=t}^N \gamma^{n-t} r_n \)
  - 越远的奖励影响越小
- **2.5 Version 3：减去基线（Baseline）**
  - 奖励是相对的，减去 baseline \( b \) 让优势有正有负
- **2.6 Policy Gradient 训练流程**
  - 收集数据 → 计算优势 → 更新参数 → 重新收集数据
  - 问题：每次更新后数据分布变化，需重新采样

---

### Unit 3：On-policy vs Off-policy 与 PPO

**目标**：理解数据收集与训练策略的关系，认识 PPO。

- **3.1 On-policy**
  - 训练用的 Actor 和交互用的 Actor 是同一个
- **3.2 Off-policy**
  - 训练用的 Actor 和交互用的 Actor 可以不同
  - 优点：可重复使用旧数据
- **3.3 Proximal Policy Optimization (PPO)**
  - 训练中的 Actor 需要知道它与交互 Actor 的差异
  - 避免更新过大导致策略崩溃
- **3.4 探索（Exploration）**
  - 数据收集时需要随机性（如输出熵、参数噪声）
  - 否则可能永远学不到更好的策略

---

### Unit 4：Actor-Critic（演员-评论家）

**目标**：结合 Value-based 和 Policy-based 方法，提升训练效率。

- **4.1 Critic 是什么？**
  - 给定 Actor \( \theta \)，评价在状态 \( s \) 下有多好
  - 价值函数 \( V^\theta(s) \)： discounted cumulated reward 的期望
- **4.2 估计 \( V^\theta(s) \) 的方法**
  - Monte-Carlo (MC)：看完整个 episode 再算累积奖励
  - Temporal-difference (TD)：用下一步的价值估计当前价值
- **4.3 MC vs TD**
  - MC 方差大、无偏；TD 方差小、有偏
- **4.4 Version 3.5：用 Critic 作为 Baseline**
  - \( A_t = G_t' - V^\theta(s_t) \)
  - 优势函数：判断动作比“平均”好还是差
- **4.5 Actor-Critic 技巧**
  - Actor 和 Critic 可以共享网络参数
- **4.6 延伸：Deep Q Network (DQN)**
  - 用神经网络估计 Q 值，适合离散动作空间

---

### Unit 5：Reward Shaping（奖励塑形）

**目标**：解决稀疏奖励问题，引导 Agent 学习。

- **5.1 稀疏奖励问题**
  - 大多数情况下 \( r_t = 0 \)，不知道动作好坏
  - 例如：机器人拧螺丝
- **5.2 Reward Shaping**
  - 开发者定义额外奖励引导 Agent
  - 例如：VizDoom 中的 living penalty、health pickup reward
- **5.3 好奇心驱动探索（Curiosity Driven Exploration）**
  - 看到新事物时给予额外奖励
  - 基于自监督预测

---

### Unit 6：No Reward — Learning from Demonstration

**目标**：在没有奖励函数时，通过专家示范学习。

- **6.1 模仿学习（Imitation Learning）**
  - Actor 可以与环境交互，但没有 reward
  - 有专家的示范轨迹 \( \hat{t} \)
  - 问题：专家只采样有限状态，Agent 可能复制无关行为
- **6.2 逆强化学习（Inverse Reinforcement Learning, IRL）**
  - 原则：老师永远是最好的
  - 基本思想：先学 reward function，再用 RL 找 optimal actor
- **6.3 IRL 与 GAN 的类比**
  - Actor = Generator，Reward Function = Discriminator
  - 专家轨迹得分高，Actor 轨迹得分低
- **6.4 延伸阅读**
  - Guided Cost Learning (ICML 2016)
  - Visual Reinforcement Learning with Imagined Goals (NIPS 2018)
  - Skew-Fit (ICML 2020)

---

### Unit 7：总结与延伸

**目标**：回顾整体框架，了解后续学习方向。

- **7.1 课程总结**
  - What is RL?（三步骤）
  - Policy Gradient
  - Actor-Critic
  - Sparse Reward
  - No Reward: Learning from Demonstration
- **7.2 延伸学习**
  - DQN 系列（DDQN, Prioritized DDQN, Dueling DDQN, A3C, Rainbow）
  - PPO 应用（DeepMind, OpenAI）
  - 机器人学习（Chelsea Finn 等）

---

## 📌 学习建议

1. **按 Unit 顺序学习**：每个 Unit 都建立在前一个基础上。
2. **重点理解 Version 0 → 3.5 的演进**：这是 Policy Gradient 的核心。
3. **动手推导公式**：如 \( G_t' \)、\( A_t \)、\( V^\theta(s) \) 的更新。
4. **结合示例理解**：Space Invader、AlphaGo、VizDoom、机器人拧螺丝。
5. **延伸阅读论文**：IRL、RIG、Skew-Fit 等。

如果你需要，我可以继续帮你把每个 Unit 拆成更细的知识点、公式推导或代码实现。
