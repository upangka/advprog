# 运行Java代码

```sh
j! hello.java
jbang hello.java

# 指定版本
jbang --java 25 hello.java
j! --java 25 hello.java
```

# Java文件格式

`jbang init xxx.java`生成的文件，顶部有下面

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
```

可以直接运行

```sh
./xxx.java
```
