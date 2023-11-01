package vn.sparkminds.services;

import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.User;
import vn.sparkminds.services.dto.request.AddUserRequest;
import vn.sparkminds.services.dto.request.UpdateUserRequest;
import vn.sparkminds.services.dto.response.UserResponse;

public interface UserService {
    public UserResponse signupUser(AddUserRequest user) throws UserException;

    public UserResponse findUserByEmail(String email) throws UserException;

    public UserResponse updateUser(User user, UpdateUserRequest req) throws UserException;

    public UserResponse findUserById(Long userId) throws UserException;

    public User findUserByJwt(String jwt) throws UserException;
}
