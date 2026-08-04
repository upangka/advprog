///usr/bin/env jbang "$0" "$@" ; exit $?
//JAVA 25+

class Book {
	public String title;
	public Library library;
	public static Book last = null;

	public Book(String name) {
		title = name;
		last = this;
	}

	public static String lastBookTitle() {
		return last.title;
	}

	public String getTitle() {
		return title;
	}
}

class Library {
	public Book[] books;
	public int index;
	public static int totalBooks = 0;

	public Library(int size) {
		books = new Book[size];
		index = 0;
	}

	public void addBook(Book book) {
		books[index] = book;
		index++;
		totalBooks++;
		book.library = this;
	}
}

void main(String[] args) {
	System.out.println(Library.totalBooks); // 0

	System.out.println(Book.lastBookTitle()); // error 因为last为null

	// System.out.println(Book.getTitle());    // error 因为getTitle是实例方法

	Book goneGirl = new Book("Gone Girl");
	Book fightClub = new Book("Fight Club");

	System.out.println(goneGirl.title); // Gone Girl

	System.out.println(Book.lastBookTitle()); // Fight Club

	System.out.println(fightClub.lastBookTitle()); // Fight Club

	System.out.println(goneGirl.last.title); // Fight Club

	Library libraryA = new Library(1);
	Library libraryB = new Library(2);
	libraryA.addBook(goneGirl);

	System.out.println(libraryA.index); // 1

	System.out.println(libraryA.totalBooks); // 1

	libraryA.totalBooks = 0;
	libraryB.addBook(fightClub);
	libraryB.addBook(goneGirl);

	System.out.println(libraryB.index); // 2

	System.out.println(Library.totalBooks); // 2

	System.out.println(goneGirl.library.books[0].title); // Gone Girl
}