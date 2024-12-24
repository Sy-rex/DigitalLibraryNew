package com.sobolev.spring.controllers;

import com.sobolev.spring.models.Book;
import com.sobolev.spring.models.Person;
import com.sobolev.spring.services.BookService;
import com.sobolev.spring.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final PeopleService peopleService;

    @Autowired
    public BookController(BookService bookService, PeopleService peopleService) {
        this.bookService = bookService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("book",bookService.index());
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @GetMapping("/{id}")
    public String showById(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        model.addAttribute("book",bookService.findById(id));
        model.addAttribute("people",peopleService.index());
        model.addAttribute("pers",bookService.findOwner(id));
        return "books/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book",bookService.findById(id));
        return "books/edit";
    }

    @PostMapping()
    public String createBook(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PatchMapping("/{id}")
    public String updateBook(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        bookService.update(id, book);
        return "redirect:/books";
    }
}
