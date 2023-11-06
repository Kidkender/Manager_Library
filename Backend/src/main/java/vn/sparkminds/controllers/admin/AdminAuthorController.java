package vn.sparkminds.controllers.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import vn.sparkminds.exceptions.AuthorException;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.Author;
import vn.sparkminds.model.User;
import vn.sparkminds.services.AuthorService;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.request.AuthorRequest;
import vn.sparkminds.services.dto.response.ApiResponse;
import vn.sparkminds.services.dto.response.AuthorResponse;

@RestController
@RequestMapping("/api/admin/authors")
public class AdminAuthorController {
    @Autowired
    private AuthorService authorService;

    @Autowired
    private UserService userService;


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Author> createAuthorHandler(@RequestHeader("Authorization") String jwt,
            @Valid @RequestBody Author req) throws UserException {
        User user = userService.findUserByJwt(jwt);
        Author createdAuthor = authorService.createAuthor(req);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<AuthorResponse> updateAuthorHandler(
            @RequestHeader("Authorization") String jwt, @Valid @RequestBody AuthorRequest req,
            @PathVariable("id") Long id) throws AuthorException, UserException {

        User user = userService.findUserByJwt(jwt);
        AuthorResponse response = authorService.updateAuthor(id, req);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteAuthorHandler(
            @RequestHeader("Authorization") String jwt, @PathVariable("id") Long id)
            throws AuthorException, UserException {

        User user = userService.findUserByJwt(jwt);
        authorService.deleteAuthor(id);
        ApiResponse response = new ApiResponse("Delete successfully", true);
        return new ResponseEntity<ApiResponse>(response, HttpStatus.OK);

    }

    @PostMapping("/add/multiple")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<Author>> addMutipleAuthor(
            @RequestHeader("Authorization") String authorization,
            @Valid @RequestBody Author[] authors) throws UserException {
        User user = userService.findUserByJwt(authorization);
        List<Author> authorList = authorService.createMultipleAuthor(authors);
        return new ResponseEntity<>(authorList, HttpStatus.OK);
    }
}
