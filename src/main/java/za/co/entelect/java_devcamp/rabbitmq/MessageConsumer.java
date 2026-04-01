package za.co.entelect.java_devcamp.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import za.co.entelect.java_devcamp.configs.RabbitConfig;
import za.co.entelect.java_devcamp.request.FulfillmentRequest;
import za.co.entelect.java_devcamp.response.FulfilmentResponse;
import za.co.entelect.java_devcamp.service.IFulfilmentService;
import za.co.entelect.java_devcamp.util.MaskingUtils;
import za.co.entelect.java_devcamp.webclient.KYCWebService;
import za.co.entelect.java_devcamp.webclientdto.KYCCheckDto;

import static org.flywaydb.core.internal.util.JsonUtils.parseJson;


@Slf4j
@Service
@AllArgsConstructor
public class MessageConsumer {

    private final KYCWebService kycWebService;
    private final IFulfilmentService iFulfilmentService;
    private final ObjectMapper objectMapper;

//    @RabbitListener(queues = RabbitConfig.QUEUE_NAME, ackMode = "MANUAL")
//    public void listen(Message message, Channel channel) throws Exception {
//        try {
//            String body = new String(message.getBody(), StandardCharsets.UTF_8);
//            System.out.println("Received message: " + body);
//
//            // Optional delay for debugging
//            Thread.sleep(3000); // 3 seconds
//
//            // Acknowledge message
//            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        } catch (Exception e) {
//            // Requeue if something goes wrong
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
//        }
//    }

    @RabbitListener(queues = RabbitConfig.KYC_QUEUE)
    public void listenToKYCQueue(String message) {
        try {
            FulfillmentRequest request = objectMapper.readValue(message, FulfillmentRequest.class);
            request.setOrderId(2L);
            log.info("Processing KYC check for customer with Id ending in: {}", MaskingUtils.maskId(request.getCustomerIdNumber()));

            KYCCheckDto kycResponse = kycWebService.getCustomerKYC(request.getCustomerIdNumber());
            FulfilmentResponse fulfilmentResponse = new FulfilmentResponse(request.getOrderId(), request.getCorrelationId(), request.getCustomerIdNumber(), false);

            iFulfilmentService.processKYCCheckResponse(kycResponse, fulfilmentResponse);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
