package app.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class BadRequestException extends RuntimeException {

    private HttpStatus status;
    private String message;

    public BadRequestException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}
