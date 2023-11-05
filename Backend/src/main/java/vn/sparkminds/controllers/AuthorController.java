package vn.sparkminds.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RestController;
import jakarta.websocket.server.PathParam;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.Author;
import vn.sparkminds.model.User;
import vn.sparkminds.services.AuthorService;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.request.AuthorRequest;
import vn.sparkminds.services.dto.response.ApiResponse;
import vn.sparkminds.services.dto.response.AuthorResponse;

@RequestMapping("/api/v1/authors")
@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private UserService userService;

    @PostMapping
    @PreAuthorize("")
    public ResponseEntity<Author> createAuthorHandler(@RequestHeader("Authorization") String jwt,
            @RequestBody Author req) throws UserException {
        User user = userService.findUserByJwt(jwt);
        Author createdAuthor = authorService.createAuthor(req);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorResponse> updateAuthorHandler(
            @RequestHeader("Authorization") String jwt, @RequestBody AuthorRequest req,
            @PathVariable("id") Long id) throws AuthorException, UserException {

        User user = userService.findUserByJwt(jwt);
        AuthorResponse response = authorService.updateAuthor(id, req);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAuthorHandler(
            @RequestHeader("Authorization") String jwt, @PathVariable("id") Long id)
            throws AuthorException, UserException {

        User user = userService.findUserByJwt(jwt);
        authorService.deleteAuthor(id);
        ApiResponse response = new ApiResponse("Delete successfully", true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);

    }

    @GetMapping("/")
    public ResponseEntity<List<AuthorResponse>> getAllAuthorHandler(
            @RequestHeader("Authorization") String jwt) {
        List<AuthorResponse> response = authorService.findAllAuthor();
        return new ResponseEntity<List<AuthorResponse>>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorByIdHandler(@PathVariable("id") Long id,
            @RequestHeader("Authorization") String jwt) throws AuthorException {
        Author response = authorService.findAuthorById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<AuthorResponse>> searchAuthorHandler(
            @PathParam("keyword") String keyword) {
        List<AuthorResponse> response = authorService.findAuthorsByKeyword(keyword);
        return new ResponseEntity<List<AuthorResponse>>(response, HttpStatus.OK);
    }


}
