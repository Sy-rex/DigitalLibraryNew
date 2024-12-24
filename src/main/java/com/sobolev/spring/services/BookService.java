package com.sobolev.spring.services;

import com.sobolev.spring.models.Book;
import com.sobolev.spring.models.Person;
import com.sobolev.spring.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> index() {
        return bookRepository.findAll();
    }

    @Transactional
    public void save(Book book) {
        bookRepository.save(book);
    }

    public Book findById(int id){
        return bookRepository.findById(id);
    }

    @Transactional
    public void delete(int id) {
        bookRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Book book) {
        book.setId(id);
        bookRepository.save(book);
    }

    public Person findOwner(int id){
        Book book = bookRepository.findById(id);
        System.out.println(book);
        System.out.println(bookRepository.findById(id).getOwner());
        return bookRepository.findById(id).getOwner();
    }
}
