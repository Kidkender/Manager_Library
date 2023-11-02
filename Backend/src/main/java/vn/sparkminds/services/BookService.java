package vn.sparkminds.services;

import java.util.List;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.model.Book;
import vn.sparkminds.services.dto.request.BookRequest;
import vn.sparkminds.services.dto.response.BookResponse;

public interface BookService {
    public Book createBook(Book book);

    public void deleteBook(Long id) throws BookException;

    public Book getBookById(Long id) throws BookException;

    public List<BookResponse> getAllBook();

    public List<BookResponse> searchBooks(String query) throws BookException;

    public BookResponse updateBook(Long bookId, BookRequest req) throws BookException;

    public List<BookResponse> findBookByCategory(Long categoryId)
            throws BookException, CategoryException;

    public List<BookResponse> findBookByAuthor(Long authorId) throws AuthorException;

    public List<BookResponse> findBookByCategoryAuthor(Long category, Long AuthorId);

    public void importMutilBooks(Book[] req);

    public List<Book> importMutilBookFromCsv(String path);
}
