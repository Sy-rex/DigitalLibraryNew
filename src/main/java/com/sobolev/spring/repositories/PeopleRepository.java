package com.sobolev.spring.repositories;

import com.sobolev.spring.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.books WHERE p.id = :id")
    Optional<Person> findByIdWithBooks(@Param("id") int id);
}
