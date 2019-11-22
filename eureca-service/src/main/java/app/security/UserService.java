package app.security;

import app.dto.auth.MongoUserDetails;
import app.dto.auth.User;
import app.exception.BadRequestException;
import app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);

        if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
        }

        String[] authorities = user.getRole().stream().map(role -> "ROLE_" + role.getRole()).toArray(String[]::new);

        return new MongoUserDetails(user.getEmail(), user.getPassword(), user.getActive(), user.isLocked(), user.isExpired(), user.isEnabled(), authorities);
    }
}
