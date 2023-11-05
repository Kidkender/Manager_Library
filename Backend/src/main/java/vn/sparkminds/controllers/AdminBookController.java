package vn.sparkminds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import org.springframework.web.multipart.MultipartFile;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.exceptions.BookException;
import vn.sparkminds.exceptions.CategoryException;
import vn.sparkminds.exceptions.PublisherException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.Book;
import vn.sparkminds.model.User;
import vn.sparkminds.services.BookService;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.request.AddBookRequest;
import vn.sparkminds.services.dto.request.BookRequest;
import vn.sparkminds.services.dto.response.ApiResponse;
import vn.sparkminds.services.dto.response.BookResponse;
import vn.sparkminds.utils.CSVHelper;

@RestController
@RequestMapping("/api/admin/books")
public class AdminBookController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Book> createBookHandler(@RequestHeader("Authorization") String jwt,
            @RequestBody AddBookRequest req)
            throws UserException, AuthorException, CategoryException, PublisherException {
        User user = userService.findUserByJwt(jwt);

        Book createdBook = bookService.createBook(req);
        return new ResponseEntity<Book>(createdBook, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<BookResponse> updateBookHandler(@PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt, @RequestBody BookRequest req)
            throws BookException, UserException {
        User user = userService.findUserByJwt(jwt);
        BookResponse updatedBook = bookService.updateBook(id, req);
        return new ResponseEntity<BookResponse>(updatedBook, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteBookHandler(@PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt) throws BookException, UserException {
        User user = userService.findUserByJwt(jwt);
        bookService.deleteBook(id);
        ApiResponse response =
                new ApiResponse("Delete book with id " + id + " successfully !", true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/creates")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> createMutilpleBookHandler(
            @RequestHeader("Authorization") String authorization, @RequestBody AddBookRequest[] req)
            throws UserException, AuthorException, CategoryException, PublisherException {
        User user = userService.findUserByJwt(authorization);
        for (AddBookRequest book : req) {
            bookService.createBook(book);
        }
        ApiResponse response = new ApiResponse("Create mutiple book successfully", true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> upLoadFileHandler(@RequestParam("file") MultipartFile file) {
        String message = "";
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                bookService.importMutilBookFromCsv(file);
                message = "Uploaded the file successfully : " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(message, true));
            } catch (Exception e) {
                message = "Could not upload the file : " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                        .body(new ApiResponse(message, false));
            }
        }
        message = "Please upload a csv file !";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(message, false));
    }

    @GetMapping("/export")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Resource> exportCsvHandler() {
        String fileName = "books.csv";
        InputStreamResource file = new InputStreamResource(bookService.exportBooksToCsv());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentType(MediaType.parseMediaType("application/csv")).body(file);
    }
}
