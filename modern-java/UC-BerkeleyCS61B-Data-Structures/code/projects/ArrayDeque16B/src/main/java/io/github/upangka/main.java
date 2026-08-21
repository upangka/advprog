import io.github.upangka.cs61b.ArrayDeque61B;
import io.github.upangka.cs61b.Deque61B;

void main() {
    Deque61B<String> deque = new ArrayDeque61B<>();

    deque.addFirst("Structure");
    deque.addFirst("Data");
    deque.addFirst("SP26");
    deque.addFirst("61B");
    deque.addFirst("CS");
    deque.addFirst("UCBerkeley");
    // [CS,61B,SP26,Data,Structure, _ , _ ,UCBerkeley]
    System.out.println(deque);
}