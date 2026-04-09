package za.co.entelect.java_devcamp.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import za.co.entelect.java_devcamp.configs.RabbitConfig;
import za.co.entelect.java_devcamp.model.Notification;
import za.co.entelect.java_devcamp.model.Result;
import za.co.entelect.java_devcamp.response.FulfilmentResponse;
import za.co.entelect.java_devcamp.serviceinterface.INotificationService;
import za.co.entelect.java_devcamp.serviceinterface.IOrderService;
import za.co.entelect.java_devcamp.util.NotificationContent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ResultAggregator {
    private final Map<String, Map<String, Result>> store = new ConcurrentHashMap<>();
    private final IOrderService iOrderService;

    public ResultAggregator(IOrderService iOrderService) {
        this.iOrderService = iOrderService;
    }

    @RabbitListener(queues = RabbitConfig.RESULTA_QUEUE)
    public void handleTypeAResult(Result result, FulfilmentResponse response) {
        if(result.getStatus().equals("PASS")){
            response.setSuccessful(true);
            iOrderService.completeOrder(response);
        }
        response.setSuccessful(false);
        iOrderService.completeOrder(response);
    }

    @RabbitListener(queues = RabbitConfig.RESULTB_QUEUE)
    public void handleResult(Result result, FulfilmentResponse response) {

        String requestId = result.getRequestID();

        store.putIfAbsent(requestId, new ConcurrentHashMap<>());
        Map<String, Result> results = store.get(requestId);

        results.put(result.getFulfillmentCheck(), result);

        if (results.size() == 4) {

            boolean allPassed = results.values().stream()
                    .allMatch(r -> "PASS".equals(r.getStatus()));
            if (allPassed) {
                log.info("FulfilmentType B passes");
                response.setSuccessful(true);
                iOrderService.completeOrder(response);

            } else {
                log.info("FulfilmentType B at least one failure");
                response.setSuccessful(false);
                iOrderService.completeOrder(response);
            }
            store.remove(requestId);
        }
    }

    @RabbitListener(queues = RabbitConfig.RESULTC_QUEUE)
    public void handleCResult(Result result, FulfilmentResponse response) {

        String requestId = result.getRequestID();

        store.putIfAbsent(requestId, new ConcurrentHashMap<>());
        Map<String, Result> results = store.get(requestId);

        results.put(result.getFulfillmentCheck(), result);

        if (results.size() == 6) {

            boolean allPassed = results.values().stream()
                    .allMatch(r -> "PASS".equals(r.getStatus()));

            if (allPassed) {
                log.info("FulfilmentType C passes");
                response.setSuccessful(true);
                iOrderService.completeOrder(response);
            } else {
                log.info("FulfilmentType C at least one failure");
                response.setSuccessful(false);
                iOrderService.completeOrder(response);
            }
            store.remove(requestId);
        }
    }
}