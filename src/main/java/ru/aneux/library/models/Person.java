package ru.aneux.library.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    @Pattern(regexp = "[а-яА-Я]{2,50}", message = "Имя должно включать от 2 до 50 русских символов")
    private String firstName;

    @Column(name = "second_name")
    @Pattern(regexp = "[а-яА-Я]{2,50}", message = "Отчество должно включать от 2 до 50 русских символов")
    private String secondName;

    @Column(name = "last_name")
    @Pattern(regexp = "[а-яА-Я]{2,50}", message = "Фамилия должна включать от 2 до 50 русских символов")
    private String lastName;

    @Column(name = "birth_year")
    @NotNull(message = "Год рождения должен быть указан")
    @Min(value = 1920, message = "Год рождения не может быть меньше 1920")
    private Integer birthYear;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() { }

    public Person(String firstName, String secondName, String lastName, Integer birthYear) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.birthYear = birthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return String.format("%s %s %s", firstName, secondName, lastName);
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
