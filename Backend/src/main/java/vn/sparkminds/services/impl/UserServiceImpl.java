package vn.sparkminds.services.impl;

import java.time.LocalDateTime;
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
import vn.sparkminds.services.dto.request.AddUserRequest;
import vn.sparkminds.services.dto.request.UpdateUserRequest;
import vn.sparkminds.services.dto.response.UserResponse;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private static UserRepository userRepository;

    @Autowired
    private static JwtTokenProvider jwtTokenProvider;

    @Autowired
    private static PasswordEncoder passwordEncoder;

    @Autowired
    private static UserMapper userMapper;;

    @Override
    public UserResponse findUserById(Long userId) throws UserException {
        User opt = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with Id: " + userId));
        return userMapper.toUserResponse(opt);
    }

    @Override
    public User findUserByJwt(String jwt) throws UserException {
        JwtTokenClaims tokenClaims = jwtTokenProvider.getClaimsFromToken(jwt);
        String email = tokenClaims.getUserName();
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isPresent()) {
            return opt.get();
        }
        throw new UserException("Invalid token...");
    }

    @Override
    public UserResponse signupUser(AddUserRequest user) throws UserException {
        // TODO Auto-generated method stub
        User isEmailExist = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new UserException("Email is already exist"));
        User isUserNameExist = userRepository.findByName(user.getUsername())
                .orElseThrow(() -> new UserException("Username is already exist"));
        User newUser = new User();
        newUser.setEmail(user.getEmail());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setName(user.getUsername());
        newUser.setRole(Role.USER);
        newUser.setCreateAt(LocalDateTime.now());
        User created = userRepository.save(newUser);
        return userMapper.toUserResponse(created);
    }

    @Override
    public UserResponse updateUser(User user, UpdateUserRequest req) throws UserException {
        // TODO Auto-generated method stub
        if (req.getPassword() != null) {

            user.setPassword(req.getPassword());
        }
        if (req.getAddress() != null) {
            user.setAddress(req.getAddress());
        }
        if (req.getUsername() != null) {
            user.setName(req.getUsername());
        }
        User updated = userRepository.save(user);
        return userMapper.toUserResponse(updated);
    }

    @Override
    public UserResponse findUserByEmail(String email) throws UserException {
        // TODO Auto-generated method stub
        Optional<User> opt = userRepository.findByEmail(email);
        if (opt.isPresent()) {
            return userMapper.toUserResponse(opt.get());
        }
        throw new UserException("Could not find user with email " + email);
    }


}
