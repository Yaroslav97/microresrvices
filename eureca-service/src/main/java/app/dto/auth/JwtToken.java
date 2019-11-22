package app.dto.auth;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class JwtToken {

    @Id
    private String token;

    public JwtToken() {
    }

    public JwtToken(String token) {
        this.token = token;
    }

}
