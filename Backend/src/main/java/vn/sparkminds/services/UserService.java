package vn.sparkminds.services;

import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User findUserByJwt(String jwt) throws UserException;
}
