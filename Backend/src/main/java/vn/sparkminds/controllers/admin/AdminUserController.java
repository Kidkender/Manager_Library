package vn.sparkminds.controllers.admin;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.User;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.response.UserResponse;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/admin/users")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsersHandler(
            @RequestHeader("Authorization") String authorization) throws UserException {
        User user = userService.findUserByJwt(authorization);
        List<UserResponse> response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("keyword") String keyword,
            @RequestHeader("Authorization") String authorization) throws UserException {
        User user = userService.findUserByJwt(authorization);
        List<User> users = userService.searchUsers(keyword);
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserResponse> getUserById(
            @RequestHeader("Authorization") String authorization, @PathVariable("id") Long id)
            throws UserException {
        User user = userService.findUserByJwt(authorization);

        UserResponse userSearch = userService.findUserById(id);
        return new ResponseEntity<>(userSearch, HttpStatus.OK);
    }
}
