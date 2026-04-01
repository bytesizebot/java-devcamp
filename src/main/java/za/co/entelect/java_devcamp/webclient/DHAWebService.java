package za.co.entelect.java_devcamp.webclient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class DHAWebService {
    
    private final WebClient dhaClient;
    private final HttpServletRequest request;

    public DHAWebService(WebClient.Builder dhaClientBuilder, HttpServletRequest request){
        this.dhaClient = dhaClientBuilder
                .baseUrl("http://localhost:8082")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.request = request;
    }
}
