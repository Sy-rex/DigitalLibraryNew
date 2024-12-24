package com.sobolev.spring.controllers;

import com.sobolev.spring.models.Person;
import com.sobolev.spring.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("person",peopleService.index());
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson (@ModelAttribute("person") Person person) {
        return "people/new";
    }

    @GetMapping("/{id}")
    public String showPerson (@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        return "people/show";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", peopleService.findById(id));
        return "people/edit";
    }

    @PostMapping()
    public String createPerson (@ModelAttribute("person") Person person) {
        peopleService.save(person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson (@PathVariable("id") int id) {
        peopleService.deleteById(id);
        return "redirect:/people";
    }

    @PatchMapping("/{id}")
    public String updatePerson (@PathVariable("id") int id, Person person) {
        peopleService.updateById(id, person);
        return "redirect:/people";
    }
}
