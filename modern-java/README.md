
# jbang
快速演示，和运行

```sh
jbang init xxx.java
jbang edit -b xxx.java
```

## jbang debug

首先在命令行执行
```sh
$ jbang run --debug src/dev/jbang/fmt/Main.java /home/pkmer/projects/advprog/modern-java/io/github/upangka/hello.java
```
会输出

```sh
Listening for transport dt_socket at address: 4004
```

在vscode中配置launch.json

```json
{
    "type": "java",
    "name": "Debug (Launch) - Main",
    "request": "attach",
    "hostName": "localhost",
    "port": 4004
}
```


# 资料

- [JDK25](https://docs.oracle.com/en/java/javase/25/language/text-blocks.html)