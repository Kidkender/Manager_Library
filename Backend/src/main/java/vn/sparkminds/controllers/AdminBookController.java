package vn.sparkminds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.Book;
import vn.sparkminds.model.User;
import vn.sparkminds.services.BookService;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.request.BookRequest;
import vn.sparkminds.services.dto.response.ApiResponse;
import vn.sparkminds.services.dto.response.BookResponse;

@RestController
@RequestMapping("/api/admin/books")
public class AdminBookController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBookHandler(@RequestHeader("Authorization") String jwt,
            Book req) throws UserException {
        User user = userService.findUserByJwt(jwt);

        Book createdBook = bookService.createBook(req);
        return new ResponseEntity<Book>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<BookResponse> updateBookHandler(@PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt, @RequestBody BookRequest req)
            throws BookException, UserException {
        User user = userService.findUserByJwt(jwt);
        BookResponse updatedBook = bookService.updateBook(id, req);
        return new ResponseEntity<BookResponse>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteBookHandler(@PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt) throws BookException, UserException {
        User user = userService.findUserByJwt(jwt);
        bookService.deleteBook(id);
        ApiResponse response = new ApiResponse(jwt, true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/creates")
    public ResponseEntity<ApiResponse> createMutilpleBookHandler(
            @RequestHeader("Authorization") String authorization, @RequestBody Book[] req)
            throws UserException {
        User user = userService.findUserByJwt(authorization);
        bookService.importMutilBooks(req);
        ApiResponse response = new ApiResponse("Create mutiple book successfully", true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

}
