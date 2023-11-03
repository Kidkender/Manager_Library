package vn.sparkminds.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.User;
import vn.sparkminds.model.enums.Role;
import vn.sparkminds.repositories.UserRepository;
import vn.sparkminds.security.JwtTokenClaims;
import vn.sparkminds.security.JwtTokenProvider;
import vn.sparkminds.services.UserService;
import vn.sparkminds.services.dto.mapper.UserMapper;
import vn.sparkminds.services.dto.request.UpdateUserRequest;
import vn.sparkminds.services.dto.response.UserResponse;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;;

    @Override
    public UserResponse findUserById(Long userId) throws UserException {
        User opt = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with Id: " + userId));
        return userMapper.toUserResponse(opt);
    }

    @Override
    public User findUserByJwt(String jwt) throws UserException {

        jwt = jwt.substring(7);
        JwtTokenClaims tokenClaims = jwtTokenProvider.getClaimsFromToken(jwt);
        String email = tokenClaims.getUserName();
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("Invalid token...");
    }

    @Override
    public User signupUser(User req) throws UserException {
        Optional<User> isEmailExist = userRepository.findByEmail(req.getEmail());
        if (isEmailExist.isPresent()) {
            throw new UserException("Email is already in use");
        }

        Optional<User> isUserNameExist = userRepository.findByUserName(req.getName());
        if (isUserNameExist.isPresent()) {
            throw new UserException("User name is already in use");
        }
        User newUser = new User();
        newUser.setEmail(req.getEmail());
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        newUser.setUserName(req.getUserName());
        newUser.setRole(Role.USER);
        newUser.setCreateAt(LocalDateTime.now());
        return userRepository.save(newUser);
        // return userMapper.toUserResponse(created);
    }

    @Override
    public UserResponse updateUser(String jwt, UpdateUserRequest req) throws UserException {
        if (Objects.isNull(req)) {
            throw new IllegalStateException("Invalid data");
        }

        User user = findUserByJwt(jwt);
        if (req.getAddress() != null) {
            user.setAddress(req.getAddress());
        }
        if (req.getUserName() != null) {
            user.setUserName(req.getUserName());
        }
        if (req.getPhoneNumber() != null) {
            user.setPhone(req.getPhoneNumber());
        }
        if (req.getName() != null) {
            user.setName(req.getName());
        }
        user.setUpdateAt(LocalDateTime.now());
        User updated = userRepository.save(user);
        return userMapper.toUserResponse(updated);
    }

    @Override
    public User findUserByEmail(String email) throws UserException {

        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("Could not find user with email " + email);
    }

    @Override
    public List<User> searchUsers(String query) throws UserException {
        List<User> users = userRepository.findByQuery(query);
        if (users.size() == 0) {
            throw new UserException("No users found for query " + query);
        }
        return users;
    }

    @Override
    public String changeEmail(String jwt, String email) throws UserException {
        User update = findUserByJwt(jwt);
        update.setEmail(email);
        update.setUpdateAt(LocalDateTime.now());
        User updated = userRepository.save(update);
        return "Change email to " + updated.getEmail() + " successfully !!!";
    }

    @Override
    public List<UserResponse> getAllUsers() throws UserException {
        List<User> users = userRepository.findAll();

        return users.stream().map(user -> userMapper.toUserResponse(user)).toList();
    }

    @Override
    public void resetPassword(String jwt, String password) throws UserException {
        User user = findUserByJwt(jwt);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }


}
