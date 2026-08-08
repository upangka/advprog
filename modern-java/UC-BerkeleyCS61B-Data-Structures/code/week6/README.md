# Disjoint Set(Union Find)

A Disjoint-Sets (or Union-Find) data structure keeps track of a fixed number of elements partitioned into a number of disjoint sets. The data structure has two operations:

> 这个集合只做两件事
>
> 如何快速判断两个元素是否属于同一个集合，以及如何高效地把两个集合合并在一起。

1. `connect(x, y)`：把 `x` 和 `y` 连接起来,也叫union
2. `isConnected(x, y)`：判断 `x` 和 `y` 是否连通。

"Connections can be transitive" 说明 `isConnected` 不要求 `x` 和 `y` 之间必须有直接的 `connect` 调用，只要它们之间存在一条经过其他节点的路径，就认为它们连通。

# QuickFind
