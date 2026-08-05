# IntNode

节点就是节点，它只管存数据和指向下一个节点。操作节点的方法，应该由另一个类`SList`来管

```java
class IntNode{
    private int item;
    private IntNode next;

    public IntNode(int item,IntNode next){
        this.item = item;
        this.next = next;
    }
}
```
