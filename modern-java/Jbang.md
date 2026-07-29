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

## 文件顶部配置

注意没有空格

```java
///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+
//DEPS org.projectlombok:lombok:1.18.46
//DEPS tools.jackson.core:jackson-databind:3.2.1
//REPOS aliyun=https://maven.aliyun.com/repository/central
//JAVAC_OPTIONS -proc:full
```
