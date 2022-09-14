package ru.aneux.library.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aneux.library.models.Book;
import ru.aneux.library.models.Person;
import ru.aneux.library.services.BooksService;
import ru.aneux.library.services.PeopleService;
import ru.aneux.library.util.BookValidator;

@Controller
@RequestMapping("/books")
public class BooksController {
	private final BooksService booksService;
	private final BookValidator bookValidator;
	private final PeopleService peopleService;

	@Autowired
	public BooksController(BookValidator bookValidator, BooksService booksService, PeopleService peopleService) {
		this.booksService = booksService;
		this.bookValidator = bookValidator;
		this.peopleService = peopleService;
	}

	@GetMapping
	public String index(@RequestParam(value = "page", required = false) Integer page,
						@RequestParam(value = "books_per_page", required = false) Integer booksPerPage,
						@RequestParam(value = "sort_by_publishing_year", required = false) boolean sortByPublishingYear,
						Model model) {
		model.addAttribute("books", page == null || booksPerPage == null ? booksService.findAll(sortByPublishingYear)
				: booksService.findAllWithPagination(page, booksPerPage, sortByPublishingYear));
		return "books/index";
	}

	@GetMapping("/{id}")
	public String showBook(@PathVariable("id") int id, Model model) {
		// As @ModelAttribute sets id for newly created Person object (from @PathVariable value), which causes strange
		// behaviour in the drop-down menu, we should manually create that person by constructor and pass it to the model
		model.addAttribute("person", new Person());

		Book book = booksService.findOne(id);
		model.addAttribute("book", book);
		if (book.getOwner() == null)
			model.addAttribute("people", peopleService.findAll());
		return "books/book";
	}

	@GetMapping("/search")
	public String search(@RequestParam(value = "query", defaultValue = "") String query, Model model) {
		model.addAttribute("query", query);
		if (!query.isEmpty())
			model.addAttribute("found_books", booksService.findBooksByTitle(query));
		return "books/search";
	}

	@PatchMapping("/{id}/assign")
	public String assignBook(@PathVariable("id") int bookId, @ModelAttribute("person") Person person) {
		booksService.assignBook(bookId, person);
		return "redirect:/books/" + bookId;
	}

	@PatchMapping("/{id}/release")
	public String releaseBook(@PathVariable("id") int id) {
		booksService.releaseBook(id);
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

		return "redirect:/books/" + booksService.save(book);
	}

	@GetMapping("/{id}/edit")
	public String editBook(@PathVariable("id") int id, Model model) {
		model.addAttribute("book", booksService.findOne(id));
		return "books/edit_book";
	}

	@PatchMapping("/{id}")
	public String updateBook(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
							 BindingResult bindingResult) {
		// Check if we should do additional validation (if book's parameters have been changed)
		Book originBook = booksService.findOne(id);
		if (!book.equals(originBook))
			bookValidator.validate(book, bindingResult);
		if (bindingResult.hasErrors())
			return "books/edit_book";

		// Set additional fields that weren't applied in the form
		book.setOwner(originBook.getOwner());
		book.setTakenAt(originBook.getTakenAt());
		return "redirect:/books/" + booksService.save(book);
	}

	@DeleteMapping("/{id}")
	public String deleteBook(@PathVariable("id") int id) {
		booksService.delete(id);
		return "redirect:/books";
	}
}
