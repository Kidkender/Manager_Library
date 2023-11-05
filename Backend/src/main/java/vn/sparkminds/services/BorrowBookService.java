package vn.sparkminds.services;

import java.util.List;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.BorrowedBookException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.BorrowedBook;
import vn.sparkminds.services.dto.response.BorrowBookResponse;

public interface BorrowBookService {
    public BorrowBookResponse borrowBook(String jwt, Long bookId)
            throws UserException, BookException;


    public void returnBook(String jwt, Long bookId) throws UserException, BookException;

    public List<BorrowedBook> getAllBorrowedBook();


    public BorrowedBook getBorrowedBookById(Long id) throws BorrowedBookException;
}
