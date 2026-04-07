package za.co.entelect.java_devcamp.soap;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import za.co.entelect.java_devcamp.wsdl.CreditCheck;
import za.co.entelect.java_devcamp.wsdl.CreditCheckResponse;

@Slf4j
@Component
@AllArgsConstructor
public class CreditClient extends WebServiceGatewaySupport {
    public CreditCheckResponse getCreditCheck(Integer customerId){
        CreditCheck request = new CreditCheck();
        request.setCustomerId(customerId);
        log.info("Requesting credit check for customer");

        String remoteUrl = "http://localhost:8083/CreditCheck";

        CreditCheckResponse response =  (CreditCheckResponse) getWebServiceTemplate()
                .marshalSendAndReceive(remoteUrl, request,
                        new SoapActionCallback(
                                "http://java_devcamp.entelect.co.za/product-house/CreditCheckRequest"));
        return response;
    }
}
