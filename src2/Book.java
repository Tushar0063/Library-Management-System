package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private int copies;

    public Book(int id, String title, String author, String isbn, String category, int copies) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.copies = copies;
    }

    public Book(String title, String author, String isbn, String category, int copies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.copies = copies;
    }

    // Getters and setters (auto-generate in your IDE)
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getCategory() { return category; }
    public int getCopies() { return copies; }

    public void setId(int id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setCategory(String category) { this.category = category; }
    public void setCopies(int copies) { this.copies = copies; }
}
