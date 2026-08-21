import io.github.upangka.cs61b.ArrayDeque61B;
import io.github.upangka.cs61b.Deque61B;

void main() {
    Deque61B<String> deque = new ArrayDeque61B<>() {{
        addLast("a");
        addLast("b");
        addFirst("c");
        addLast("d");
        addLast("e");
        addFirst("f");
    }};

    System.out.println("Backing array: %s".formatted(deque));
    System.out.println("User mental model: %s".formatted(deque.toList()));
}