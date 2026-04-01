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
public class CreditCheckWebService {

    private final WebClient ccClient;
    private final HttpServletRequest request;

    public CreditCheckWebService(WebClient.Builder ccClientBuilder, HttpServletRequest request){
        this.ccClient = ccClientBuilder
                .baseUrl("http://localhost:8083")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.request = request;
    }
}
