package vn.sparkminds.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.Book;
import vn.sparkminds.services.BookService;
import vn.sparkminds.services.BorrowBookService;
import vn.sparkminds.services.dto.response.ApiResponse;
import vn.sparkminds.services.dto.response.BookResponse;
import vn.sparkminds.services.dto.response.BorrowBookResponse;

@RequestMapping("/api/v1/books")
@RestController
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private BorrowBookService borrowBookService;

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookByIdHandler(@PathVariable("id") Long id)
            throws BookException {
        Book book = bookService.getBookById(id);
        return new ResponseEntity<Book>(book, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookResponse>> getAllBookHandler() {
        List<BookResponse> bookResponse = bookService.getAllBook();
        return new ResponseEntity<List<BookResponse>>(bookResponse, HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<BookResponse>> getBooksByCategoryHandler(@PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt) throws BookException, CategoryException {

        List<BookResponse> books = bookService.findBookByCategory(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@PathVariable("id") Long id)
            throws AuthorException {
        List<BookResponse> books = bookService.findBookByAuthor(id);
        return new ResponseEntity<List<BookResponse>>(books, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBooksHandler(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("search") String search) throws BookException {
        List<BookResponse> books = bookService.searchBooks(search);

        return new ResponseEntity<List<BookResponse>>(books, HttpStatus.OK);
    }

    @PutMapping("/{id}/borrow")
    public ResponseEntity<BorrowBookResponse> borrowBookHandler(
            @RequestHeader("Authorization") String authorization, @PathVariable Long id)
            throws UserException, BookException {
        BorrowBookResponse borrowBookResponse = borrowBookService.borrowBook(authorization, id);
        return new ResponseEntity<BorrowBookResponse>(borrowBookResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<ApiResponse> returnBookHandler(
            @RequestHeader("Authorization") String authorization, @PathVariable Long id)
            throws UserException, BookException {
        borrowBookService.returnBook(authorization, id);
        ApiResponse response = new ApiResponse("Return book successfully", true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }
}
