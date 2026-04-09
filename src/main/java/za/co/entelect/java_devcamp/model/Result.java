package za.co.entelect.java_devcamp.model;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class Result {
    private String requestID;
    private String service;
    private String fulfillmentCheck;
    private String status;
}
