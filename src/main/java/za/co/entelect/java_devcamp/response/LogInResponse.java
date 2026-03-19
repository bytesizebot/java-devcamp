package za.co.entelect.java_devcamp.response;
import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogInResponse {

    private String token;
    private String username;
    private String message;

    public LogInResponse(String message) {

    }
}

