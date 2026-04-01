package za.co.entelect.java_devcamp.webclient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import za.co.entelect.java_devcamp.webclientdto.KYCCheckDto;

@Slf4j
@Service
public class KYCWebService {

    private final WebClient kycClient;
    private final HttpServletRequest request;

    public KYCWebService(WebClient.Builder kycClientBuilder, HttpServletRequest request){
        this.kycClient = kycClientBuilder
                .baseUrl("http://localhost:8081")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.request = request;
    }

    public KYCCheckDto getCustomerKYC(String customerId){
        log.info("Getting customer KYC information");

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        try{
            return kycClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/kyc/")
                            .queryParam("customerId", customerId)
                            .build())
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError(),
                            response -> {
                                if (response.statusCode() == HttpStatus.NOT_FOUND) {
                                    return Mono.empty();
                                }
                                return response.createException();
                            }
                    )
                    .bodyToMono(KYCCheckDto.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw e;
        }
    }
}
