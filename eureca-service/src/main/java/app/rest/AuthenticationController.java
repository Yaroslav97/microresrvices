package app.rest;

import app.dto.auth.AuthResponse;
import app.dto.auth.LoginRequest;
import app.dto.auth.User;
import app.service.LoginService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Api
@Slf4j
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private LoginService loginService;

    @CrossOrigin("*")
    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signIn(@RequestBody LoginRequest loginRequest) {

        String token = loginService.login(loginRequest.getUsername(), loginRequest.getPassword());

        List<String> headerList = new ArrayList<>();
        headerList.add("Content-Type");
        headerList.add(" Accept");
        headerList.add("X-Requested-With");
        headerList.add("Authorization");

        List<String> exposeList = new ArrayList<>();
        exposeList.add("Authorization");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccessControlExposeHeaders(exposeList);
        headers.setAccessControlAllowHeaders(headerList);
        headers.set("Authorization", token);

        return new ResponseEntity<>(new AuthResponse(token), headers, HttpStatus.CREATED);
    }

    @CrossOrigin("*")
    @PostMapping("/signup")
    public ResponseEntity<Object> signUp(@RequestBody User user) {

        loginService.saveUser(user);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @CrossOrigin("*")
    @PostMapping("/signout")
    public ResponseEntity<AuthResponse> signOut(@RequestHeader(value = "Authorization") String token) {

        HttpHeaders headers = new HttpHeaders();

        if (loginService.logout(token)) {
            headers.remove("Authorization");
            return new ResponseEntity<>(new AuthResponse("logged out"), headers, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(new AuthResponse("Logout Failed"), headers, HttpStatus.NOT_MODIFIED);
    }


    @PostMapping("/valid/token")
    public Boolean isValidToken(@RequestHeader(value = "Authorization") String token) {
        return true;
    }

    @CrossOrigin("*")
    @PostMapping("/signin/token")
    public ResponseEntity<AuthResponse> createNewToken(@RequestHeader(value = "Authorization") String token) {

        String newToken = loginService.createNewToken(token);

        HttpHeaders headers = new HttpHeaders();
        List<String> headerList = new ArrayList<>();
        List<String> exposeList = Collections.singletonList("Authorization");

        headerList.add("Content-Type");
        headerList.add(" Accept");
        headerList.add("X-Requested-With");
        headerList.add("Authorization");

        headers.setAccessControlAllowHeaders(headerList);
        headers.setAccessControlExposeHeaders(exposeList);
        headers.set("Authorization", newToken);

        return new ResponseEntity<>(new AuthResponse(newToken), headers, HttpStatus.CREATED);
    }

}