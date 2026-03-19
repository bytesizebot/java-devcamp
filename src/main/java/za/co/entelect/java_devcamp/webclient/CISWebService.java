package za.co.entelect.java_devcamp.webclient;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import za.co.entelect.java_devcamp.customerdto.CustomerDto;
import za.co.entelect.java_devcamp.dto.ProfileDto;

import static org.springframework.web.reactive.function.client.ExchangeFilterFunctions.basicAuthentication;

@Slf4j
@Service
public class CISWebService {

    private final WebClient webClient;
    private final HttpServletRequest request;

    public CISWebService(WebClient.Builder webClientBuilder, HttpServletRequest request) {
        this.webClient = webClientBuilder
                .baseUrl("http://localhost:8084")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        this.request = request;
    }

    public CustomerDto getCustomerByEmail(String email) {
        log.info("Attempting to retrieve customer with email: " + email);

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Bearer token used:" + authHeader);

        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/customer")
                            .queryParam("emailAddress", email)
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, authHeader)
                    .retrieve()
                    .bodyToMono(CustomerDto.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw e;
        }
    }

    public ProfileDto registerCustomer(ProfileDto profileDto) {
        log.info("Registering a profile with the cis");

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("Bearer token used:" + authHeader);

        try {
            return webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/customer/")
                            .queryParam("verbose", true)
                            .build())
                    .header(HttpHeaders.AUTHORIZATION, authHeader)
                    .bodyValue(profileDto)
                    .retrieve()
                    .bodyToMono(ProfileDto.class)
                    .block();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
            throw e;
        }
    }
}

