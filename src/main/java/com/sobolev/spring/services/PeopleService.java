package com.sobolev.spring.services;

import com.sobolev.spring.models.Person;
import com.sobolev.spring.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> index(){
        return peopleRepository.findAll();
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    public Person findById(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteById(int id){
        peopleRepository.deleteById(id);
    }

    @Transactional
    public void updateById(int id, Person person){
        person.setId(id);
        peopleRepository.save(person);
    }
}
