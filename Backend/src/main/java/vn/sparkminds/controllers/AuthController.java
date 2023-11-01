package vn.sparkminds.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.request.AddUserRequest;
import vn.sparkminds.services.dto.response.UserResponse;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUpHandler(@RequestBody AddUserRequest req)
            throws UserException {
        UserResponse created = userService.signupUser(req);
        return new ResponseEntity<UserResponse>(created, HttpStatus.CREATED);
    }

    @GetMapping("/signin")
    public ResponseEntity<UserResponse> signInHandler(Authentication auth) throws UserException {
        UserResponse user = userService.findUserByEmail(auth.getName());
        return new ResponseEntity<UserResponse>(user, HttpStatus.OK);
    }
}
