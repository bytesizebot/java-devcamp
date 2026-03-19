package za.co.entelect.java_devcamp.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.*;

@Getter
@Setter
@Component
public class AuthServiceConfig {

    @Value("${auth.service.url}")
    private String url;

    @Value("${auth.service.client.username}")
    private String clientUsername;

    @Value("${auth.service.client.password}")
    private String clientPassword;

}