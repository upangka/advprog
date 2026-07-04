# logger的父子关系

1. logger层级中name="root"(RootLogger)是顶级的Logger就像类中`object`一样。
2. 父子关系通过`.`来关联，比如`a`是`a.b`的父，`a.b`是`a.b.c`的父

```python
>>> import logging
>>> root = logging.root
>>> # 或者这样得到root
>>> root = logging.getLogger()
>>> logger_a = logging.getLogger("a")
>>> logger_ab = logging.getLogger("a.b")
>>> logger_abc = logging.getLogger("a.b.c")
>>> # 验证父子关系
>>> logger_abc.parent is logger_ab
True
>>> logger_ab.parent is logger_a
True
>>> logger_a.parent is root
True
>>> logger_abc.parent.parent.parent is root
True
>>> # root的子级
>>> root.manager.loggerDict.keys()
dict_keys(['a', 'a.b', 'a.b.c'])
```


## RootLogger

1. logging模块内部有一个`root`(RootLogger的实例，`name="root"`)
2. logging模块的函数`info`,`error`等，都是委托root这个**模块单例**进行操作的。

```python
>>> import logging
>>> # 可以看到默认的LEVEL级别是WARNING
>>> logging.root
<RootLogger root (WARNING)>
>>> # 通过basicConfig配置一下root
>>> logging.basicConfig(level=logging.DEBUG)
>>> # 成功修改root的LEVEL
>>> logging.root
<RootLogger root (DEBUG)>
>>> # 查看logger的name
>>> logging.root.name
'root'
```

### 源码分析

```python
class RootLogger(Logger):
    ...
```


logging.basicConfig(logging模块的basicConfig函数),最主要的就是配置logging模块中的root，添加了一个StreamHandler

```python
def basicConfig():
    """
    The default behaviour is to create a StreamHandler which writes to
    sys.stderr, set a formatter using the BASIC_FORMAT format string, and
    add the handler to the root logger.
    """
    ...
```

logging.info(logging模块的info函数)
```python
def info(msg, *args, **kwargs):
    """
    Log a message with severity 'INFO' on the root logger. If the logger has
    no handlers, call basicConfig() to add a console handler with a pre-defined
    format.
    """
    if len(root.handlers) == 0:
        basicConfig() # 在这里配置了handle
    root.info(msg, *args, **kwargs)
```