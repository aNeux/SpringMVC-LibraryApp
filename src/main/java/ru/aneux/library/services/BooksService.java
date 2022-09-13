package ru.aneux.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aneux.library.models.Book;
import ru.aneux.library.models.Person;
import ru.aneux.library.repositories.BooksRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
	private final BooksRepository booksRepository;

	@Autowired
	public BooksService(BooksRepository booksRepository) {
		this.booksRepository = booksRepository;
	}

	public List<Book> findAll(boolean sortByPublishingYear) {
		return !sortByPublishingYear ? booksRepository.findAll() : booksRepository.findAll(Sort.by("publishingYear"));
	}

	public List<Book> findAllWithPagination(int page, int booksPerPage, boolean sortByPublishingYear) {
		PageRequest pageRequest = !sortByPublishingYear ? PageRequest.of(page, booksPerPage)
				: PageRequest.of(page, booksPerPage, Sort.by("publishingYear"));
		return booksRepository.findAll(pageRequest).getContent();
	}

	public Book findOne(int id) {
		return booksRepository.findById(id).orElse(null);
	}

	public List<Book> findBooksByTitle(String titleQuery) {
		return booksRepository.findByTitleContaining(titleQuery);
	}

	public boolean checkBookExistence(Book book) {
		return !booksRepository.findByTitleAndAuthorAndPublishingYear(book.getTitle(),
				book.getAuthor(), book.getPublishingYear()).isEmpty();
	}

	@Transactional
	public int save(Book book) {
		return booksRepository.save(book).getId();
	}

	@Transactional
	public void assignBook(int bookId, Person person) {
		Optional<Book> bookOptional = booksRepository.findById(bookId);
		if (bookOptional.isPresent()) {
			Book book = bookOptional.get();
			book.setOwner(person);
			book.setTakenAt(new Date());
		}
	}

	@Transactional
	public void releaseBook(int id) {
		Optional<Book> bookOptional = booksRepository.findById(id);
		if (bookOptional.isPresent()) {
			Book book = bookOptional.get();
			book.setOwner(null);
			book.setTakenAt(null);
		}
	}

	@Transactional
	public void delete(int id) {
		booksRepository.deleteById(id);
	}
}
