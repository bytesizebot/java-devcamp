package za.co.entelect.java_devcamp.configs;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String QUEUE_NAME = "ProductFulfilment";
    public static final String EXCHANGE_NAME = "FulfilmentExchange";

    //Queues

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routing.key.#");
    }

    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.durable("delayed-queue")
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", "my-queue")
                .withArgument("x-message-ttl", 5000) // 5000 ms = 5 seconds
                .build();
    }
}
