一个粒子探测器的例子：记录一天中能量最高的 `M` 个粒子。如果存下所有粒子再排序，内存是 `Θ(N)`，而用 `MinPQ` 只维护 `M` 个元素，内存是 `Θ(M)`。这就是 PQ 的核心价值：**在数据流中实时追踪“最好的”几个**

---

https://chat.deepseek.com/a/chat/s/71ab9b4d-5d6a-49fb-bfbf-8e3d2ba2ca06

https://berkeleycs-web-archive.vercel.app/cs61b/sp20/index.html

https://github.com/yanyanran/cs61b/blob/main/HEAPandPQ/MaxHeap.java
