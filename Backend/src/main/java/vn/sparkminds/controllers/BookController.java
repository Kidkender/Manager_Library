package vn.sparkminds.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.model.Book;
import vn.sparkminds.services.BookService;
import vn.sparkminds.services.dto.response.BookResponse;

@RequestMapping("/api/v1/books")
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

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
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@RequestParam("id") Long id)
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

}
