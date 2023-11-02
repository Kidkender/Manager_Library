package vn.sparkminds.services;

import java.util.List;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.User;
import vn.sparkminds.services.dto.request.UpdateUserRequest;
import vn.sparkminds.services.dto.response.UserResponse;

public interface UserService {
    public User signupUser(User user) throws UserException;

    public User findUserByEmail(String email) throws UserException;

    public UserResponse updateUser(String jwt, UpdateUserRequest req) throws UserException;

    public UserResponse findUserById(Long userId) throws UserException;

    public List<User> searchUsers(String query) throws UserException;

    public User findUserByJwt(String jwt) throws UserException;

    public void resetPassword(String jwt, String password) throws UserException;

    public String changeEmail(String jwt, String email) throws UserException;

    public List<UserResponse> getAllUsers() throws UserException;
}
