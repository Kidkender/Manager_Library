package vn.sparkminds.controllers;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.request.UpdateUserRequest;
import vn.sparkminds.services.dto.response.ApiResponse;
import vn.sparkminds.services.dto.response.UserResponse;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/update/profile")
    public ResponseEntity<UserResponse> updateUserHandler(
            @RequestHeader("Authorization") String authorization,
            @RequestBody UpdateUserRequest req) throws UserException {
        UserResponse updated = userService.updateUser(authorization, req);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @PutMapping("/update/email")
    public ResponseEntity<ApiResponse> changeEmailHandler(
            @RequestHeader("Authorization") String authorization, @RequestParam String email)
            throws UserException {

        String message = userService.changeEmail(authorization, email);
        ApiResponse response = new ApiResponse(message, true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/reset/password")
    public ResponseEntity<ApiResponse> resetPasswordHandler(
            @RequestHeader("Authorization") String authorization,
            @RequestBody Map<String, String> passwordData) throws UserException {
        String password = passwordData.get("password");

        userService.resetPassword(authorization, password);
        ApiResponse response = new ApiResponse("Change password successfully", true);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
