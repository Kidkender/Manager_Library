package vn.sparkminds.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.model.Book;
import vn.sparkminds.model.Category;
import vn.sparkminds.repositories.BookRepository;
import vn.sparkminds.repositories.CategoryRepository;
import vn.sparkminds.services.BookService;
import vn.sparkminds.services.dto.request.BookRequest;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;

    public BookServiceImpl(BookRepository bookRepository, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
    }



    @Override
    public Book createBook(Book book) {
        // TODO Auto-generated method stub
        return null;
    }



    @Override
    public List<Book> getAllBook() {
        // TODO Auto-generated method stub

        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) throws BookException {
        Optional<Book> opt = bookRepository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new BookException("Book is not found with id " + id);
    }

    @Override
    public Book updateBook(Long bookId, BookRequest req) throws BookException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Book> findBookByAuthor(Long authorId) throws AuthorException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Book> findBookByCategory(Long categoryId) throws BookException, CategoryException {
        List<Book> bookList = new ArrayList<Book>();
        return null;
    }



    @Override
    public String deleteBook(Long id) throws BookException {
        Book book = getBookById(id);
        bookRepository.delete(book);
        return "Delete Book successfully";
    }

}
