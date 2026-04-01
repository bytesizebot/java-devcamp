package za.co.entelect.java_devcamp.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

