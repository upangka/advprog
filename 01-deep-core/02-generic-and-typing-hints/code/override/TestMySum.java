import java.util.Arrays;
import java.util.List;

/**
 * 测试 MySum 类的所有方法
 */
public class TestMySum {

    private static int testCount = 0;
    private static int passCount = 0;

    public static void main(String[] args) {
        MySum mySum = new MySum();

        // 测试1: mymax(T first, T[] rest) - 基本类型数组
        testArrayBasic(mySum);

        // 测试2: mymax(T first, T[] rest, Function key) - 带key函数的数组
        testArrayWithKey(mySum);

        // 测试3: mymax(Iterable<T> iterable) - 基本类型可迭代对象
        testIterableBasic(mySum);

        // 测试4: mymax(Iterable<T> iterable, Function key) - 带key函数的可迭代对象
        testIterableWithKey(mySum);

        // 测试5: mymax(Iterable<T> iterable, DT defaultValue) - 带默认值的可迭代对象
        testIterableWithDefault(mySum);

        // 测试6: mymax(Iterable<T> iterable, Function key, DT defaultValue) - 完整版本
        testIterableWithKeyAndDefault(mySum);

        // 测试7: 自定义对象测试
        testCustomObjects(mySum);

        // 测试8: 空集合异常测试
        testEmptyCollections(mySum);

        // 打印测试结果
        System.out.println("\n==========================================");
        System.out.println("测试完成: " + passCount + "/" + testCount + " 通过");
        if (passCount == testCount) {
            System.out.println("✅ 所有测试通过！");
        } else {
            System.out.println("❌ 有 " + (testCount - passCount) + " 个测试失败");
        }
    }

    // 测试 mymax(T first, T[] rest)
    private static void testArrayBasic(MySum mySum) {
        System.out.println("=== 测试 mymax(T first, T[] rest) ===");

        // 整数数组
        Integer[] intArray = { 5, 2, 8, 1, 9, 3 };
        Integer maxInt = mySum.mymax(intArray[0],
                Arrays.copyOfRange(intArray, 1, intArray.length));
        assertEquals("整数数组最大值", 9, maxInt);

        // 字符串数组
        String[] strArray = { "apple", "zebra", "banana", "yellow" };
        String maxStr = mySum.mymax(strArray[0],
                Arrays.copyOfRange(strArray, 1, strArray.length));
        assertEquals("字符串数组最大值", "zebra", maxStr);

        // 双精度数组
        Double[] doubleArray = { 3.14, 2.71, 1.41, 4.0 };
        Double maxDouble = mySum.mymax(doubleArray[0],
                Arrays.copyOfRange(doubleArray, 1, doubleArray.length));
        assertEquals("双精度数组最大值", 4.0, maxDouble);
    }

    // 测试 mymax(T first, T[] rest, Function key)
    private static void testArrayWithKey(MySum mySum) {
        System.out.println("\n=== 测试 mymax(T first, T[] rest, Function key) ===");

        // 按字符串长度找最大值
        String[] strArray = { "a", "abc", "ab", "abcd", "ab" };
        String longest = mySum.mymax(strArray[0],
                Arrays.copyOfRange(strArray, 1, strArray.length),
                String::length);
        assertEquals("按长度找最长字符串", "abcd", longest);

        // 按绝对值找最大值
        Integer[] intArray = { -5, 2, -8, 1, -3 };
        Integer maxAbs = mySum.mymax(intArray[0],
                Arrays.copyOfRange(intArray, 1, intArray.length),
                Math::abs);
        assertEquals("按绝对值找最大值", -8, maxAbs);
    }

    // 测试 mymax(Iterable<T> iterable)
    private static void testIterableBasic(MySum mySum) {
        System.out.println("\n=== 测试 mymax(Iterable<T> iterable) ===");

        // List集合
        List<Integer> intList = Arrays.asList(5, 2, 8, 1, 9, 3);
        Integer maxInt = mySum.mymax(intList);
        assertEquals("List整数最大值", 9, maxInt);

        // 字符串List
        List<String> strList = Arrays.asList("hello", "world", "java", "python");
        String maxStr = mySum.mymax(strList);
        assertEquals("List字符串最大值", "world", maxStr);

        // 单个元素的集合
        List<Double> singleList = Arrays.asList(3.14);
        Double maxSingle = mySum.mymax(singleList);
        assertEquals("单元素集合最大值", 3.14, maxSingle);
    }

    // 测试 mymax(Iterable<T> iterable, Function key)
    private static void testIterableWithKey(MySum mySum) {
        System.out.println("\n=== 测试 mymax(Iterable<T> iterable, Function key) ===");

        // 按字符串长度找最大值
        List<String> strList = Arrays.asList("cat", "elephant", "dog", "hippopotamus");
        String longest = mySum.mymax(strList, String::length);
        assertEquals("按长度找最长字符串", "hippopotamus", longest);

        // 自定义对象的某个属性
        List<Person> people = Arrays.asList(
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Charlie", 20));
        Person oldest = mySum.mymax(people, Person::age);
        assertNotNull("最年长的人不应为空", oldest);
        assertEquals("最年长的人年龄", 30, oldest.age());
    }

    // 测试 mymax(Iterable<T> iterable, DT defaultValue)
    private static void testIterableWithDefault(MySum mySum) {
        System.out.println("\n=== 测试 mymax(Iterable<T> iterable, DT defaultValue) ===");

        // 正常集合
        List<Integer> intList = Arrays.asList(1, 5, 3, 7, 2);
        Object result1 = mySum.mymax(intList, -1);
        assertEquals("非空集合返回最大值", 7, (Integer) result1);

        // 空集合返回默认值
        List<Integer> emptyList = Arrays.asList();
        Object result2 = mySum.mymax(emptyList, -1);
        assertEquals("空集合返回默认值", -1, (Integer) result2);

        // 字符串默认值
        Object result3 = mySum.mymax(emptyList, MySum.MSG);
        assertEquals("空集合返回字符串默认值", MySum.MSG, (String) result3);
    }

    // 测试 mymax(Iterable<T> iterable, Function key, DT defaultValue)
    private static void testIterableWithKeyAndDefault(MySum mySum) {
        System.out.println("\n=== 测试 mymax(Iterable<T> iterable, Function key, DT defaultValue) ===");

        // 带key函数的正常集合
        List<String> strList = Arrays.asList("a", "bbb", "cc", "dddd");
        Object result1 = mySum.mymax(strList, String::length, "default");
        assertEquals("带key的非空集合", "dddd", (String) result1);

        // 带key函数的空集合
        List<String> emptyList = Arrays.asList();
        Object result2 = mySum.mymax(emptyList, String::length, "NO_DATA");
        assertEquals("带key的空集合返回默认值", "NO_DATA", (String) result2);

        // 自定义对象和默认值
        List<Person> emptyPeople = Arrays.asList();
        Person defaultPerson = new Person("Default", 0);
        Object result3 = mySum.mymax(emptyPeople, Person::age, defaultPerson);
        assertEquals("自定义空集合返回默认对象", defaultPerson, (Person) result3);
    }

    // 测试自定义对象
    private static void testCustomObjects(MySum mySum) {
        System.out.println("\n=== 测试自定义对象 ===");

        // 测试实现Comparable的自定义类
        List<Student> students = Arrays.asList(
                new Student("Alice", 85),
                new Student("Bob", 92),
                new Student("Charlie", 78));

        Student topStudent = new MySum().mymax(students);
        assertNotNull("成绩最好的学生不应为空", topStudent);
        assertEquals("成绩最好的学生", "Bob", topStudent.name());
        assertEquals("最高分", 92, topStudent.score());

        // 按姓名长度找
        Student longestName = new MySum().<Student, Integer>mymax(students, s -> s.name().length());
        assertEquals("名字最长的学生", "Charlie", longestName.name());
    }

    // 测试空集合异常
    private static void testEmptyCollections(MySum mySum) {
        System.out.println("\n=== 测试空集合异常 ===");

        // 测试不带默认值的方法抛异常
        boolean exceptionThrown = false;
        try {
            List<Integer> emptyList = Arrays.asList();
            mySum.mymax(emptyList);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
            assertEquals("异常消息正确", "max() arg is en empty sequence", e.getMessage());
        }
        assertTrue("空集合应抛出IllegalArgumentException", exceptionThrown);

        // 测试带key的空集合抛异常
        exceptionThrown = false;
        try {
            List<String> emptyList = Arrays.asList();
            mySum.mymax(emptyList, String::length);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue("带key的空集合应抛出异常", exceptionThrown);
    }

    // 辅助断言方法
    private static void assertEquals(String testName, Object expected, Object actual) {
        testCount++;
        if (expected == null ? actual == null : expected.equals(actual)) {
            passCount++;
            System.out.println("  ✓ " + testName + ": 通过");
        } else {
            System.out.println("  ✗ " + testName + ": 失败 - 期望 " + expected + ", 实际 " + actual);
        }
    }

    private static void assertTrue(String testName, boolean condition) {
        testCount++;
        if (condition) {
            passCount++;
            System.out.println("  ✓ " + testName + ": 通过");
        } else {
            System.out.println("  ✗ " + testName + ": 失败");
        }
    }

    private static void assertNotNull(String testName, Object obj) {
        testCount++;
        if (obj != null) {
            passCount++;
            System.out.println("  ✓ " + testName + ": 通过");
        } else {
            System.out.println("  ✗ " + testName + ": 失败 - 对象为null");
        }
    }

    // 测试用的Person类
    static record Person(
            String name,
            int age) {
    }

    // 测试用的Student类，实现Comparable接口
    static record Student(
            String name,
            int score) implements Comparable<Student> {
        @Override
        public int compareTo(Student other) {
            return Integer.compare(this.score, other.score);
        }

    }
}