package ru.aneux.library.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Book {
    private int id;

    @Size(min = 2, max = 100, message = "Название книги должно включать от 2 до 100 символов")
    private String title;

    @Pattern(regexp = "[а-яА-Яa-zA-Z ]{2,100}", message = "Имя автора должно включать от 2 до 100 символов")
    private String author;

    @NotNull(message = "Год издания должен быть указан")
    @Min(value = 0, message = "Год издания должен быть больше 0")
    private Integer publishingYear;

    public Book() { }

    public Book(int id, String title, String author, Integer publishingYear) {
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

    public Integer getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Integer publishingYear) {
        this.publishingYear = publishingYear;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Book book = (Book) o;
        return title.equals(book.title) && author.equals(book.author) && publishingYear.equals(book.publishingYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, publishingYear);
    }
}
