# 在环形中：处理下标的技巧

使用JDK内置方法Math.floorMod或者传统方式加上原本的除数

```java
// 现代方式
nextFirst = Math.floorMod(nextFirst - 1, items.length);
// 传统方式
nextFirst = (nextFirst - 1 + items.length) % items.length;
```

上面的两种方式是一样的效果,测试代码[IndexFrontBackTest.java](./src/test/java/io/github/upangka/cs61b/IndexFrontBackTest.java)

```java
    @Test
    public void testIndexFrontBack() {
        int size = 8;
        int nextFirst = 4, nextLast = 5;
        int[] actual = new int[size];
        int[] expected = new int[size];

        for (int i = 0; i < size; i++) {
            actual[i] = Math.floorMod(nextFirst - i, size);
            expected[i] = (nextFirst - i + size) % size;
        }

        Truth.assertThat(actual).isEqualTo(expected);
        log.info("Good Test nextFirst {}", Arrays.toString(actual));


        actual = new int[size];
        expected = new int[size];

        for (int i = 0; i < size; i++) {
            actual[i] = Math.floorMod(nextLast + i, size);
            expected[i] = (nextLast + i + size) % size;
        }

        Truth.assertThat(actual).isEqualTo(expected);
        log.info("Good Test nextLast {}", Arrays.toString(actual));
    }
```
运行结果:

```txt
Good Test nextFirst [4, 3, 2, 1, 0, 7, 6, 5]
Good Test nextLast  [5, 6, 7, 0, 1, 2, 3, 4]
```