package oop._01_basics;

/**
 * Demonstrates:
 * - Default constructor
 * - Parameterized constructor
 * - Copy constructor
 * - Constructor overloading
 */
public class ConstructorTypes {
    public static void main(String[] args) {
        System.out.println("=== Default Constructor ===");
        Book book1 = new Book();
        book1.display();

        System.out.println("\n=== Parameterized Constructor ===");
        Book book2 = new Book("1984", "George Orwell", 328);
        book2.display();

        System.out.println("\n=== Constructor with 2 parameters ===");
        Book book3 = new Book("To Kill a Mockingbird", "Harper Lee");
        book3.display();

        System.out.println("\n=== Copy Constructor ===");
        Book book4 = new Book(book2);  // Copy of book2
        book4.display();

        System.out.println("\n=== Verifying copy ===");
        System.out.println("book2 title: " + book2.title);
        System.out.println("book4 title: " + book4.title);
        System.out.println("Are they same object? " + (book2 == book4));  // false
    }
}

class Book {
    String title;
    String author;
    int pages;

    // 1. Default Constructor - No parameters
    Book() {
        this.title = "Unknown";
        this.author = "Unknown";
        this.pages = 0;
        System.out.println("Default constructor called");
    }

    // 2. Parameterized Constructor - All parameters
    Book(String title, String author, int pages) {
        this.title = title;
        this.author = author;
        this.pages = pages;
        System.out.println("Parameterized constructor (3 params) called");
    }

    // 3. Parameterized Constructor - Partial parameters (Overloading)
    Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.pages = 0;  // Default value
        System.out.println("Parameterized constructor (2 params) called");
    }

    // 4. Copy Constructor - Creates new object from existing
    Book(Book other) {
        this.title = other.title;
        this.author = other.author;
        this.pages = other.pages;
        System.out.println("Copy constructor called");
    }

    void display() {
        System.out.println("Book: " + title + " by " + author + " (" + pages + " pages)");
    }
}
