import cs61b.BSTMap;

void main() {
    var m = new BSTMap<String,Integer>();
    m.put("sumomo", 1);
    m.put("momo", 2);
    m.put("uchi", 1);
    m.put("mo", 2);
    m.put("no", 1);

    System.out.println(m);
}