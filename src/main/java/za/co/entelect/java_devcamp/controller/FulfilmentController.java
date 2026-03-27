package za.co.entelect.java_devcamp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import za.co.entelect.java_devcamp.rabbitmq.MessageProducer;

@RestController
public class FulfilmentController {

    @Autowired
    private MessageProducer messageProducer;

    @GetMapping("/send")
    public String sendMessage(@RequestParam String message){
        messageProducer.sendMessage(message);
        return "Message sent to RabbitMQ: +" + message;
    }

    @PostMapping("/send-test")
    public String sendTest(@RequestBody String message) {
        messageProducer.sendTestMessage(message);
        return "Sent test message directly to queue: " + message;
    }
}
