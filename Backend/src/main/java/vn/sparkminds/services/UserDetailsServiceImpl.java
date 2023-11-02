package vn.sparkminds.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.sparkminds.model.User;
import vn.sparkminds.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> opt = userRepository.findByEmail(username);
        if (opt.isPresent()) {
            User user = opt.get();

            List<GrantedAuthority> authorities = new ArrayList<>();


            return new org.springframework.security.core.userdetails.User(user.getEmail(),
                    user.getPassword(), authorities);
        }
        throw new BadCredentialsException("User Not Found With username" + username);
    }

}
