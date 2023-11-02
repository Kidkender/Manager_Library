package vn.sparkminds.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.User;
import vn.sparkminds.repositories.UserRepository;
import vn.sparkminds.services.UserService;

@RestController
// @RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<User> signUpHandler(@RequestBody User req) throws UserException {
        User created = userService.signupUser(req);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/signin")
    public ResponseEntity<User> signinHandler(Authentication auth) throws BadCredentialsException {
        Optional<User> opt = userRepository.findByEmail(auth.getName());
        if (opt.isPresent()) {
            return new ResponseEntity<User>(opt.get(), HttpStatus.ACCEPTED);
        }
        throw new BadCredentialsException("invalid username or password");
    }
}
