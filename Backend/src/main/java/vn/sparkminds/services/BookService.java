package vn.sparkminds.services;

import java.util.List;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.model.Book;
import vn.sparkminds.services.dto.request.BookRequest;

public interface BookService {
    public Book createBook(Book book);

    public void deleteBook(Book book) throws BookException;

    public Book getBookById(Long id) throws BookException;

    public List<Book> getAllBook();

    public Book updateBook(Long bookId, BookRequest req) throws BookException;

    public List<Book> findBookBCategory(Long categoryId) throws BookException, CategoryException;
}
