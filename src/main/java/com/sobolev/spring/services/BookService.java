package com.sobolev.spring.services;

import com.sobolev.spring.models.Book;
import com.sobolev.spring.models.Person;
import com.sobolev.spring.repositories.BookRepository;
import com.sobolev.spring.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final PeopleRepository peopleRepository;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleRepository peopleRepository) {
        this.bookRepository = bookRepository;
        this.peopleRepository = peopleRepository;
    }

    public List<Book> index() {
        return bookRepository.findAll();
    }

    public List<Book> index(boolean sortByYear) {
        if (sortByYear) {
            return bookRepository.findAll(Sort.by("yearOfFoundation"));
        }
        return bookRepository.findAll();
    }

    public List<Book> index(int page, int booksPerPage) {
        return bookRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }

    public List<Book> index(int page, int booksPerPage, boolean sortByYear) {
        return bookRepository.findAll(PageRequest.of(page,booksPerPage, Sort.by("yearOfFoundation"))).getContent();
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

        book.setRentAt(new Date());
        owner.getBooks().add(book);
        book.setOwner(owner);

        bookRepository.save(book);
        peopleRepository.save(owner);
    }

    @Transactional
    public void deleteLink(int id){
        Book book = bookRepository.findById(id);
        Person owner = book.getOwner();

        book.setRentAt(null);
        owner.getBooks().remove(book);
        book.setOwner(null);

        bookRepository.save(book);
        peopleRepository.save(owner);
    }

    public Optional<List<Book>> findByTitle(String title) {
        if (title == ""){
            return Optional.empty();
        }
        return bookRepository.findByTitleStartingWith(title);
    }
}
