package ru.aneux.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.aneux.library.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
	Optional<Book> findByTitleAndAuthorAndPublishingYear(String title, String author, int publishingYear);
	List<Book> findByTitleContaining(String titleQuery);
}
