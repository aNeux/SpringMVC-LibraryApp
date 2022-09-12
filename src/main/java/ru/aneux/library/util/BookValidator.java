package ru.aneux.library.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.aneux.library.models.Book;
import ru.aneux.library.services.BooksService;

@Component
public class BookValidator implements Validator {
    private final BooksService booksService;

    @Autowired
    public BookValidator(BooksService booksService) {
        this.booksService = booksService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (booksService.checkBookExistence((Book) target))
            errors.reject("", "Такая книга уже есть в библиотеке");
    }
}
