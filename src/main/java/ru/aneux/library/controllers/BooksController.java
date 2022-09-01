package ru.aneux.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aneux.library.dao.BookDAO;
import ru.aneux.library.dao.PersonDAO;
import ru.aneux.library.models.Book;
import ru.aneux.library.models.Person;
import ru.aneux.library.util.BookValidator;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;
    private final BookValidator bookValidator;

    private final PersonDAO personDAO;

    @Autowired
    public BooksController(BookDAO bookDAO, BookValidator bookValidator, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.bookValidator = bookValidator;
        this.personDAO = personDAO;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("books", bookDAO.getBooks());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        // FIXME: For some reason using @ModelAttribute annotation for Person object here change the default selection in the drop-down menu
        model.addAttribute("person", new Person());

        model.addAttribute("book", bookDAO.getBook(id));
        Person attachedPerson = bookDAO.getAttachedPerson(id);
        if (attachedPerson != null)
            model.addAttribute("attachedPerson", attachedPerson);
        else
            model.addAttribute("people", personDAO.getPeople());
        return "books/book";
    }

    @PatchMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int bookId, @ModelAttribute("person") Person person) {
        bookDAO.assignBook(bookId, person.getId());
        return "redirect:/books/" + bookId;
    }

    @PatchMapping("/{id}/release")
    public String releaseBook(@PathVariable("id") int id) {
        bookDAO.releaseBook(id);
        return "redirect:/books/" + id;
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new_book";
    }

    @PostMapping
    public String createBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors())
            return "books/new_book";

        Integer id = bookDAO.addBook(book);
        return "redirect:/books" + (id != null ? "/" + id : "");
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.getBook(id));
        return "books/edit_book";
    }

    @PatchMapping("/{id}")
    public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                             BindingResult bindingResult) {
        // Check if we should do additional validation (if book's parameters have been changed)
        if (!book.equals(bookDAO.getBook(id)))
            bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors())
            return "books/edit_book";

        bookDAO.updateBook(book);
        return "redirect:/books/" + id;
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }
}
