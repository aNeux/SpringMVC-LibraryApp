package ru.aneux.library.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Person {
    private int id;

    @Pattern(regexp = "[а-яА-Я]{2,50}", message = "Имя должно включать от 2 до 50 русских символов")
    private String firstName;

    @Pattern(regexp = "[а-яА-Я]{2,50}", message = "Отчество должно включать от 2 до 50 русских символов")
    private String secondName;

    @Pattern(regexp = "[а-яА-Я]{2,50}", message = "Фамилия должна включать от 2 до 50 русских символов")
    private String lastName;

    @NotNull(message = "Год рождения должен быть указан")
    @Min(value = 1920, message = "Год рождения не может быть меньше 1920")
    private Integer birthYear;

    public Person() { }

    public Person(int id, String firstName, String secondName, String lastName, Integer birthYear) {
        this.id = id;
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
}
