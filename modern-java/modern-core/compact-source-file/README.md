# Compact Source File

[JEP 512: Compact Source Files and Instance Main Methods](https://openjdk.org/jeps/512)

1. 通过`import module java.base`导入了[JavaSE Platform](https://docs.oracle.com/en/java/javase/25/docs/api/java.base/module-summary.html)的API,所以可以省去常见的导包
2. 可以配合[jbang](https://www.jbang.dev/)使用第三方库和框架
3. 不能有包名，只是方便快速验证原型，但是是允许`import package.clazz`的

> 提示：JDK25 可以直接`java Xxx.java`，就想Python那样

[TranditionClazz.java](./code/TranditionClazz.java)

```java
public class TranditionClazz {
    public static void main(String[] args) {
        System.out.println("Hello World");
    }
}
```

[CompactClazz.java](./code/CompactClazz.java)`main`方法可以不再是`public static `,直接运行这个文件也能跑起来，虽然这里的`main`是实例方法。但是只要把`main`看成是实例方法，这还是传统的类

```java
public class CompactClazz {
    void main(String... args){
        System.out.println("Hello world");
    }
}
```

[exercise_01.java](./code/exercise_01.java)进一步，连类的编写都省略了,像python一样. 补充使用`jbang`可以快速创建`compact source file`

```java
int a = 0;
static int b = 1;

static void f1(){
    // System.out.println(a);
    System.out.println(b);
}

void f2(){
    System.out.println(a);
    System.out.println(b);
    System.out.println(this.getClass());
}

void main(String... args) {
    f1();
    f2();
    System.out.println(this.getClass());
}
```

上面这个就是一个标准的`Compact Source File`文件。通过`javac exercise_01.java`编译成`exercise_01.class`，进行反编译（这里我在vscode安装Java开发的插件直接打开）可以看到Java实际处理`Compact Source File`的样子

1. 写的`main`是实例方法
2. 类是`final`不能继承，类名称为文件名，所以文件名要符合Java定义类的名称规范，不然会报错。比如(`exercise_03.java`正确，[03_exercise.java](./code/03_exercise.java)错误，运行报错`bad file name: 03_exercise`)
3. 在`compact source file`中写的方法和属性，只要没有声明`static`默认都是实例方法和属性，都能使用`this`

```java
// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.util.List;

final class exercise_01 {
   int a = 0;
   static int b = 1;

   exercise_01() {
   }

   static void f1() {
      System.out.println(b);
   }

   void f2() {
      System.out.println(this.a);
      System.out.println(b);
      System.out.println(this.getClass());
   }

   void main(String... var1) {
      f1();
      this.f2();
      System.out.println(this.getClass());
      IO.println(List.of(1, 3, 5));
   }
}
```

不能有包名[exercise_02.java](./code/mypkg/exercise_02.java)

```java
//  运行会报错 error: compact source file should not have package declaration package mypkg;
package mypkg;

void main(String... args) {
    IO.println("Hello World");
}
```
