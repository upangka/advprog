import cs61b.BSTMap;
import edu.princeton.cs.algs4.Stopwatch;

void main() {
    var m = new BSTMap<String,Integer>();
    m.put("sumomo", 1);
    m.put("momo", 2);
    m.put("uchi", 1);
    m.put("mo", 2);
    m.put("no", 1);
    var sw = new Stopwatch();

    System.out.println(m);
}