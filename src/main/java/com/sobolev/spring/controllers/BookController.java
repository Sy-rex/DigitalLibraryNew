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
import java.util.Optional;

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
    public String index(Model model,
                        @RequestParam(value = "page", required = false) Integer page,
                        @RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
                        @RequestParam(value = "sort_by_year", required = false) Boolean sortByYear) {
        if (booksPerPage != null && page != null && sortByYear != null) {
            model.addAttribute("book", bookService.index(page, booksPerPage, sortByYear));
        } else if (booksPerPage != null && page != null) {
            model.addAttribute("book",bookService.index(page, booksPerPage));
        } else if (sortByYear != null) {
            model.addAttribute("book", bookService.index(sortByYear));
        } else {
            model.addAttribute("book", bookService.index());
        }
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

    @GetMapping("/search")
    public String search(@ModelAttribute("book") Book book, Model model, @RequestParam(value = "title", required = false) String titleBook) {
        model.addAttribute("book",book);
        model.addAttribute("titleBook", titleBook);

        Optional<List<Book>> books =  bookService.findByTitle(titleBook);
        if (books.isPresent() && books.get().size() > 0) {
            model.addAttribute("isPresentBook", true);
            model.addAttribute("books", books.get());
        }else {
            model.addAttribute("isPresentBook", false);
        }

        return "books/search";
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

    @PatchMapping("/{id}/setLink")
    public String setLink(@ModelAttribute("book") Book book, @PathVariable("id") int id, @RequestParam("id") int linkId) {
        bookService.setOwner(id, linkId);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/owner")
    public String deleteOwner(@PathVariable("id") int id) {
        bookService.deleteLink(id);
        return "redirect:/books";
    }
}
