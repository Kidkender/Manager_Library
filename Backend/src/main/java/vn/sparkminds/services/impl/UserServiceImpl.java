package vn.sparkminds.services.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.sparkminds.exceptions.UserException;
import vn.sparkminds.model.User;
import vn.sparkminds.repositories.UserRepository;
import vn.sparkminds.security.JwtTokenClaims;
import vn.sparkminds.security.JwtTokenProvider;
import vn.sparkminds.services.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private static UserRepository userRepository;

    @Autowired
    private static JwtTokenProvider jwtTokenProvider;

    @Override
    public User findUserById(Long userId) throws UserException {
        User opt = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found with Id: " + userId));
        return opt;
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


}
