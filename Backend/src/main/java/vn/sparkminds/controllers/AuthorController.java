package vn.sparkminds.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.websocket.server.PathParam;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.model.Author;
import vn.sparkminds.services.AuthorService;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.response.AuthorResponse;

@RequestMapping("/api/v1/authors")
@RestController
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private UserService userService;

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
