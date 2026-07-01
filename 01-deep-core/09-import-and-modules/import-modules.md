
“模块被加载” 和 “模块名在你命名空间中可用” 是两件不同的事，而import的作用就是将模块名导入到命名空间中

```python
__import__('os').system("ls -al")

globals()['xxx']
```