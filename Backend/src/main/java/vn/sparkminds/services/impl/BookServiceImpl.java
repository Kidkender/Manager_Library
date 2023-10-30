package vn.sparkminds.services.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.model.Book;
import vn.sparkminds.services.BookService;
import vn.sparkminds.services.dto.request.BookRequest;

@Service
public class BookServiceImpl implements BookService {

    @Override
    public Book createBook(Book book) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteBook(Book book) throws BookException {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Book> findBookBCategory(Long categoryId) throws BookException, CategoryException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Book> getAllBook() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Book getBookById(Long id) throws BookException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Book updateBook(Long bookId, BookRequest req) throws BookException {
        // TODO Auto-generated method stub
        return null;
    }

}
