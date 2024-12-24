package com.sobolev.spring.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Поле Название не должно быть пустым")
    @Column(name = "title")
    private String title;

    @Column(name = "author")
    @Pattern(regexp = "^[А-ЯЁ][а-яё]+ [А-ЯЁ][а-яё]+$", message = "Автор должен быть в формате: Имя Фамилия")
    private String author;

    @Min(value = 1, message = "Год написания книги не может быть меньше 1")
    @Max(value = 2024,message = "Год написания книги не может быть больше 2024")
    @Column(name = "year_of_foundation")
    private int yearOfFoundation;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {}

    public Book(String title, int yearOfFoundation, Person owner) {
        this.title = title;
        this.yearOfFoundation = yearOfFoundation;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public @NotEmpty(message = "Поле Название не должно быть пустым") String getTitle() {
        return title;
    }

    public void setTitle(@NotEmpty(message = "Поле Название не должно быть пустым") String title) {
        this.title = title;
    }

    public int getYearOfFoundation() {
        return yearOfFoundation;
    }

    public void setYearOfFoundation(int yearOfFoundation) {
        this.yearOfFoundation = yearOfFoundation;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfFoundation=" + yearOfFoundation +
                '}';
    }
}
