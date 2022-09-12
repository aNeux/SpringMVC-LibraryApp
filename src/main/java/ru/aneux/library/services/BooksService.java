package ru.aneux.library.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.aneux.library.models.Book;
import ru.aneux.library.models.Person;
import ru.aneux.library.repositories.BooksRepository;

import java.util.List;

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
        booksRepository.findById(bookId).ifPresent(b -> b.setOwner(person));
    }

    @Transactional
    public void releaseBook(int id) {
        booksRepository.findById(id).ifPresent(b -> b.setOwner(null));
    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }
}
