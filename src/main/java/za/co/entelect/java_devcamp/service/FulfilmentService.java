package za.co.entelect.java_devcamp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.DuplicateIDDocumentCheck;
import org.openapitools.model.LivingStatus;
import org.openapitools.model.MaritalStatusResponse;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import za.co.entelect.java_devcamp.configs.RabbitConfig;
import za.co.entelect.java_devcamp.creditcheck.CreditCheckResponse;
import za.co.entelect.java_devcamp.entity.Order;
import za.co.entelect.java_devcamp.entity.OrderItem;
import za.co.entelect.java_devcamp.fraudcheck.FraudCheckResponse;
import za.co.entelect.java_devcamp.model.Result;
import za.co.entelect.java_devcamp.request.FulfillmentRequest;
import za.co.entelect.java_devcamp.response.FulfilmentResponse;
import za.co.entelect.java_devcamp.serviceinterface.IFulfilmentService;
import za.co.entelect.java_devcamp.util.ActionCompletedFulfilmentChecks;
import za.co.entelect.java_devcamp.webclientdto.KYCCheckDto;
import za.co.entelect.java_devcamp.webclientdto.TaxCompliance;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Slf4j
@Service
public class FulfilmentService implements IFulfilmentService {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    @Override
    public void determineFulfillmentCheck(Order order, Long customerId, String customerIdNumber) {
        List<OrderItem> orderItems = order.getOrderItems();
        String correlationId = UUID.randomUUID().toString();

        for (OrderItem product : orderItems) {
            String checkType = product.getProduct().getFulfilmentType().getName();
            FulfillmentRequest fulfillmentRequest = new FulfillmentRequest(customerId, customerIdNumber, "", order.getOrderId(), correlationId);

            switch (checkType) {
                case "A":
                    fulfillmentRequest.setFulfillmentType("A");
                    doTypeAChecks(fulfillmentRequest);
                    break;
                case "B":
                    fulfillmentRequest.setFulfillmentType("C");
                    doTypeBChecks(fulfillmentRequest);
                    break;
                case "C":
                    fulfillmentRequest.setFulfillmentType("C");
                    doTypeCChecks(fulfillmentRequest);
                    break;
                default:
                    throw new IllegalStateException("Unknown fulfillment check type.");
            }
        }
    }

    @Override
    public void doTypeAChecks(FulfillmentRequest request) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(request);
            rabbitTemplate.convertAndSend(RabbitConfig.KYC_QUEUE, jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doTypeBChecks(FulfillmentRequest request) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(request);
            rabbitTemplate.convertAndSend(RabbitConfig.KYC_QUEUE, jsonMessage);
            rabbitTemplate.convertAndSend(RabbitConfig.DHA_QUEUE, jsonMessage);
            rabbitTemplate.convertAndSend(RabbitConfig.FRAUD_QUEUE, jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doTypeCChecks(FulfillmentRequest request) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(request);
            rabbitTemplate.convertAndSend(RabbitConfig.KYC_QUEUE, jsonMessage);
            rabbitTemplate.convertAndSend(RabbitConfig.DHA_QUEUE, jsonMessage);
            rabbitTemplate.convertAndSend(RabbitConfig.CC_QUEUE, jsonMessage);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    //Process all the messages from queues
    @Override
    public void processKYCCheck(KYCCheckDto response, FulfilmentResponse fulfilmentResponse) {
        Set<TaxCompliance> compliant = Set.of(TaxCompliance.amber, TaxCompliance.green);

        Result kycResult = new Result(fulfilmentResponse.getCorrelationId(), "KYC Service", "KYC check", "");
        String fulfillmentType = fulfilmentResponse.getFulfillmentType();

        if (response.isPrimaryIndicator() && compliant.contains(response.getTaxCompliance())) {
            log.info("Continue process to aggregate results");
            kycResult.setStatus("PASS");
        }
        kycResult.setStatus("FAIL");

        switch (fulfillmentType) {
            case "A":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTA_QUEUE, kycResult);
                break;
            case "B":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTB_QUEUE, kycResult);
                break;
            case "C":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTC_QUEUE, kycResult);
                break;
            default:
                throw new IllegalStateException("Unknown fulfillment check type.");
        }
    }

    @Override
    public void processFraudCheck(FraudCheckResponse response, FulfilmentResponse fulfilmentResponse) {
        Result fraudResult = new Result(fulfilmentResponse.getCorrelationId(), "Fraud  Service", "Fraud check", "");

        String fulfillmentType = fulfilmentResponse.getFulfillmentType();

        if (response.getBankStatus().equals("Active") && response.getNationalStatus().equals("Valid")) {
            fraudResult.setStatus("PASS");
        }
        fraudResult.setStatus("FAIL");

        switch (fulfillmentType) {
            case "B":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTB_QUEUE, fraudResult);
                break;
            case "C":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTC_QUEUE, fraudResult);
                break;
            default:
                throw new IllegalStateException("Unknown fulfillment check type.");
        }
    }

    @Override
    public void processLivingStatusCheck(LivingStatus response, FulfilmentResponse fulfilmentResponse) {
        Result livingResult = new Result(fulfilmentResponse.getCorrelationId(), "DHA Service", "Living status check", "");
        String fulfillmentType = fulfilmentResponse.getFulfillmentType();

        if (response.getLivingStatus().getValue().equals("Alive")) {
            livingResult.setStatus("PASS");
        }

        livingResult.setStatus("FAIL");

        switch (fulfillmentType) {
            case "B":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTB_QUEUE, livingResult);
                break;
            case "C":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTC_QUEUE, livingResult);
                break;
            default:
                throw new IllegalStateException("Unknown fulfillment check type.");
        }

    }

    @Override
    public void processDuplicateIdCheck(DuplicateIDDocumentCheck response, FulfilmentResponse fulfilmentResponse) {
        Result duplicateResult = new Result(fulfilmentResponse.getCorrelationId(), "DHA Service", "Duplicate Id check", "");
        String fulfillmentType = fulfilmentResponse.getFulfillmentType();

        if (response.getHasDuplicateId().equals(false)) {
            duplicateResult.setStatus("PASS");
        }
        duplicateResult.setStatus("FAIL");

        switch (fulfillmentType) {
            case "B":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTB_QUEUE, duplicateResult);
                break;
            case "C":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTC_QUEUE, duplicateResult);
                break;
            default:
                throw new IllegalStateException("Unknown fulfillment check type.");
        }

    }

    @Override
    public void processMaritalStatusCheck(MaritalStatusResponse response, FulfilmentResponse fulfilmentResponse) {
        String status = response.getCurrentStatus() != null ? String.valueOf(response.getCurrentStatus().getStatus()) : "";

        Result maritalResult = new Result(fulfilmentResponse.getCorrelationId(), "DHA Service", "Marital status check", "");
        String fulfillmentType = fulfilmentResponse.getFulfillmentType();

        if ("Married".equals(status != null ? status.trim() : "")) {
            log.info("Customer is married");
            maritalResult.setStatus("PASS");
        }

        log.info("Customer is not married");
        maritalResult.setStatus("FAIL");

        switch (fulfillmentType) {
            case "B":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTB_QUEUE, maritalResult);
                break;
            case "C":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTC_QUEUE, maritalResult);
                break;
            default:
                throw new IllegalStateException("Unknown fulfillment check type.");
        }
    }

    @Override
    public void processCreditCheck(CreditCheckResponse response, FulfilmentResponse fulfilmentResponse) {
        Result creditResult = new Result(fulfilmentResponse.getCorrelationId(), "Credit Service", "Credit status check", "");
        String fulfillmentType = fulfilmentResponse.getFulfillmentType();

        if (response.getCreditCheckResult().equals("RED")) {
            creditResult.setStatus("FAIL");
        }

        creditResult.setStatus("PASS");

        switch (fulfillmentType) {
            case "B":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTB_QUEUE, creditResult);
                break;
            case "C":
                rabbitTemplate.convertAndSend(RabbitConfig.RESULTC_QUEUE, creditResult);
                break;
            default:
                throw new IllegalStateException("Unknown fulfillment check type.");
        }

    }
}
