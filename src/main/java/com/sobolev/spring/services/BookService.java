package com.sobolev.spring.services;

import com.sobolev.spring.models.Book;
import com.sobolev.spring.models.Person;
import com.sobolev.spring.repositories.BookRepository;
import com.sobolev.spring.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PeopleRepository personRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository personRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
        this.peopleRepository = peopleRepository;
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
        return bookRepository.findById(id).getOwner();
    }

    @Transactional
    public void setOwner(int id, int linkId) {
        Person owner = peopleRepository.findById(linkId);
        Book book = bookRepository.findById(id);

        owner.getBooks().add(book);
        book.setOwner(owner);

        bookRepository.save(book);
        peopleRepository.save(owner);
    }

    @Transactional
    public void deleteLink(int id){
        Book book = bookRepository.findById(id);
        Person owner = book.getOwner();

        owner.getBooks().remove(book);
        book.setOwner(null);

        bookRepository.save(book);
        peopleRepository.save(owner);
    }
}
