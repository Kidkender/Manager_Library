package vn.sparkminds.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.BookNotBorrowedException;
import vn.sparkminds.exceptions.BookUnavailableException;
import vn.sparkminds.exceptions.BorrowedBookException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.Book;
import vn.sparkminds.model.BorrowedBook;
import vn.sparkminds.model.User;
import vn.sparkminds.repositories.BookRepository;
import vn.sparkminds.repositories.BorrowedBookRepository;
import vn.sparkminds.services.BookService;
import vn.sparkminds.services.BorrowBookService;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.mapper.BorrowBookMapper;
import vn.sparkminds.services.dto.response.BorrowBookResponse;

@Service
public class BorrowBookServiceImpl implements BorrowBookService {

    private UserService userService;
    private BookService bookService;
    private BorrowBookMapper borrowBookMapper;
    private BookRepository bookRepository;
    private BorrowedBookRepository borrowedBookRepository;

    public BorrowBookServiceImpl(UserService userService, BookService bookService,
            BorrowBookMapper borrowBookMapper, BookRepository bookRepository,
            BorrowedBookRepository borrowedBookRepository) {
        this.userService = userService;
        this.bookService = bookService;
        this.borrowBookMapper = borrowBookMapper;
        this.bookRepository = bookRepository;
        this.borrowedBookRepository = borrowedBookRepository;
    }



    @Override
    @Transactional
    public BorrowBookResponse borrowBook(String jwt, Long bookId)
            throws UserException, BookException {
        User user = userService.findUserByJwt(jwt);
        Book book = bookService.getBookById(bookId);
        BorrowedBook existingBorrowedBook =
                borrowedBookRepository.findNotReturnedByUserAndBook(user, book);
        if (existingBorrowedBook != null) {
            throw new BookNotBorrowedException(
                    "You have already borrowed this book and it's not returned yet.");
        }
        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            bookRepository.save(book);
            BorrowedBook borrowedBook = new BorrowedBook();
            borrowedBook.setUser(user);
            borrowedBook.setBook(book);
            borrowedBook.setBorrowDate(LocalDateTime.now());
            BorrowedBook createdBorrowedBook = borrowedBookRepository.save(borrowedBook);
            BorrowBookResponse res = borrowBookMapper.toBorrowBookResponse(createdBorrowedBook);

            return res;
        } else {
            throw new BookUnavailableException("Book is not available");
        }
    }



    @Override
    @Transactional
    public void returnBook(String jwt, Long bookId) throws UserException, BookException {
        User user = userService.findUserByJwt(jwt);
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookException("Book not found"));

        BorrowedBook borrowedBook = borrowedBookRepository.findNotReturnedByUserAndBook(user, book);
        if (borrowedBook != null) {
            borrowedBook.setReturnDate(LocalDateTime.now());
            borrowedBookRepository.save(borrowedBook);
            book.setQuantity(book.getQuantity() + 1);
            bookRepository.save(book);
        } else {
            throw new BookNotBorrowedException("Book was not borrowed by the user");
        }
    }



    @Override
    public List<BorrowedBook> getAllBorrowedBook() {

        return borrowedBookRepository.findAll();
    }



    @Override
    public BorrowedBook getBorrowedBookById(Long id) throws BorrowedBookException {
        BorrowedBook book = borrowedBookRepository.findById(id)
                .orElseThrow(() -> new BorrowedBookException("Not found borrow by id " + id));
        return book;
    }
}
