package za.co.entelect.java_devcamp.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import za.co.entelect.java_devcamp.configs.RabbitConfig;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class MessageConsumer {

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
}
