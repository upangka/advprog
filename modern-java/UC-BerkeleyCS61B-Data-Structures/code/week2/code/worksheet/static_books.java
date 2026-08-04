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

void main(String... args) {
	Book book1 = new Book("The Great Gatsby");
	Book book2 = new Book("The Catcher in the Rye");
	Library library = new Library(2);
	library.addBook(book1);
	library.addBook(book2);
}
