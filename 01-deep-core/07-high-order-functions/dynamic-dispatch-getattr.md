# 动态方法调用：getattr

## 核心概念

在运行时，通过**字符串形式的方法名**来调用对象的方法。

- `self.hi(msg)`  → 静态写法（编译时确定）
- `getattr(self, 'hi')(msg)` → 动态写法（运行时确定）

两者完全等价。

## 示例

```python
class A:
    def send(self, msg):
        # mth = getattr(self,'hi')
        # mth(msg)
        getattr(self, 'hi')(msg)
        return self.msg

    def hi(self, msg):
        self.msg = f"hi {msg}"

A().send('world')  # 'hi world'
```