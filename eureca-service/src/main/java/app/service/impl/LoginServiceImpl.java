package app.service.impl;

import app.dto.auth.JwtToken;
import app.dto.auth.Role;
import app.dto.auth.User;
import app.exception.BadRequestException;
import app.repository.JwtTokenRepository;
import app.repository.UserRepository;
import app.security.JwtTokenProvider;
import app.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    @Override
    public String login(String username, String password) {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

            User user = userRepository.findByEmail(username);

            if (user == null || user.getRole() == null || user.getRole().isEmpty()) {
                throw new BadRequestException(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
            }

            return jwtTokenProvider.createToken(username, user.getRole().stream().map((Role role) -> "ROLE_" + role.getRole()).collect(Collectors.toList()));

        } catch (AuthenticationException e) {
            throw new BadRequestException(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
        }
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public boolean logout(String token) {
        jwtTokenRepository.delete(new JwtToken(token));
        return true;
    }

    @Override
    public Boolean isValidToken(String token) {
        return jwtTokenProvider.validateToken(token);
    }

    @Override
    public String createNewToken(String token) {

        String username = jwtTokenProvider.getUsername(token);
        List<String> roleList = jwtTokenProvider.getRoleList(token);

        return jwtTokenProvider.createToken(username, roleList);
    }

}