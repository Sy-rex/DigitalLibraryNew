package com.sobolev.spring.repositories;

import com.sobolev.spring.models.Book;
import com.sobolev.spring.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByOwner(Person owner);

    Book findById(int id);

    Optional<List<Book>> findByTitleStartingWith(String startingWith);

    Page<Book> findAll(Pageable pageable);

    List<Book> findAll(Sort sort);
}
