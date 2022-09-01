package ru.aneux.library.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.aneux.library.models.Book;
import ru.aneux.library.models.Person;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Book> bookRowMapper = new BeanPropertyRowMapper<>(Book.class);

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBooksForPerson(int id) {
        return jdbcTemplate.query("select * from book where attached_person = ?", bookRowMapper, id);
    }

    public List<Book> getBooks() {
        return jdbcTemplate.query("select * from book", bookRowMapper);
    }

    public Book getBook(int id) {
        return jdbcTemplate.query("select * from book where id = ?", bookRowMapper, id).stream().findAny().orElse(null);
    }

    public Person getAttachedPerson(int bookId) {
        return jdbcTemplate.query("select person.* from book join person on book.attached_person = person.id where book.id = ?",
                new BeanPropertyRowMapper<>(Person.class), bookId).stream().findAny().orElse(null);
    }

    public void assignBook(int bookId, int personId) {
        jdbcTemplate.update("update book set attached_person = ? where id = ?", personId, bookId);
    }

    public void releaseBook(int bookId) {
        jdbcTemplate.update("update book set attached_person = NULL where id = ?", bookId);
    }

    public boolean checkBookExistence(Book book) {
        return !jdbcTemplate.query("select * from book where title = ? and author = ? and publishing_year = ?",
                bookRowMapper, book.getTitle(), book.getAuthor(), book.getPublishingYear()).isEmpty();
    }

    public Integer addBook(Book book) {
        return jdbcTemplate.queryForObject("insert into book(title, author, publishing_year) values (?, ?, ?) returning id",
                Integer.class, book.getTitle(), book.getAuthor(), book.getPublishingYear());
    }

    public void updateBook(Book book) {
        jdbcTemplate.update("update book set title = ?, author = ?, publishing_year = ? where id = ?",
                book.getTitle(), book.getAuthor(), book.getPublishingYear(), book.getId());
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("delete from book where id = ?", id);
    }
}
