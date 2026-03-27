package za.co.entelect.java_devcamp.webclient;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KYCService {

    private final WebClient kycClient;
    private final HttpServletRequest request;

    public KYCService(WebClient.Builder kycClientBuilder, HttpServletRequest request){
        this.kycClient = kycClientBuilder
                .baseUrl("")
                .build();
        this.request = request;
    }


}
