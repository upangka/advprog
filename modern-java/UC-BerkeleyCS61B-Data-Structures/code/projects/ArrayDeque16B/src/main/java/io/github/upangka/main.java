import io.github.upangka.cs61b.ArrayDeque61B;
import io.github.upangka.cs61b.Deque61B;

void main() {
    Deque61B<String> deque = new ArrayDeque61B<>();

    deque.addLast("61B");
    deque.addLast("SP26");
    deque.addFirst("CS");
    deque.addFirst("UCBerkeley");
    deque.addLast("Data");
    deque.addLast("Structure");

    System.out.println(deque);
}