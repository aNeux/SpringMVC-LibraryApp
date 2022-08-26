package ru.aneux.library.models;

public class Book {
    private int id, publishingYear;
    private String title, author;

    public Book() { }

    public Book(int id, String title, String author, int publishingYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publishingYear = publishingYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(int publishingYear) {
        this.publishingYear = publishingYear;
    }
}
