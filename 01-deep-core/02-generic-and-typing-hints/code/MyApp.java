class MyApp {
    public static void main(String[] args) {
        var feature = Runtime.version().feature();
        IO.println("👋 Hello, Java " + feature);

    }
}