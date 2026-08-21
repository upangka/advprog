import io.github.upangka.cs61b.ArrayDeque61B;
import io.github.upangka.cs61b.Deque61B;

void main() {
    Deque61B<String> ad = new ArrayDeque61B<>();
    ad.addLast("front"); // after this call we expect: ["front"]
    ad.addLast("middle"); // after this call we expect: ["front", "middle"]
    ad.addLast("back"); // after this call we expect: ["front", "middle", "back"]
    System.out.println(ad.toList());
    for(var item: ad){
        System.out.println(item);
    }
}