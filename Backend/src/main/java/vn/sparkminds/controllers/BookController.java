package vn.sparkminds.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.model.Book;
import vn.sparkminds.services.BookService;
import vn.sparkminds.services.dto.request.BookRequest;
import vn.sparkminds.services.dto.response.ApiResponse;
import vn.sparkminds.services.dto.response.BookResponse;

@RequestMapping("/api/v1/books")
@RestController
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBookHandler(@RequestHeader("Authorization") String jwt,
            Book req) {
        Book createdBook = bookService.createBook(req);
        return new ResponseEntity<Book>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookResponse> updateBookHandler(@PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt, @RequestBody BookRequest req)
            throws BookException {
        BookResponse updatedBook = bookService.updateBook(id, req);
        return new ResponseEntity<BookResponse>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBookHandler(@PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt) throws BookException {
        bookService.deleteBook(id);
        ApiResponse response = new ApiResponse(jwt, true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

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

    @GetMapping("/author/search")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@RequestParam("name") String name)
            throws AuthorException {
        List<BookResponse> books = bookService.findBookByAuthor(name);
        return new ResponseEntity<List<BookResponse>>(books, HttpStatus.OK);
    }

}
