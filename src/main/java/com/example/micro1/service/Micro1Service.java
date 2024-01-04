package com.example.micro1.service;


import com.example.micro1.configuration.RabbitConfiguration;
import com.example.micro1.dao.Micro1Dao;
import lombok.extern.slf4j.Slf4j;
import org.acum.shared.MyMessage;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class Micro1Service {
    private static final String EXCHANGE_NAME = "topic_logs";
    private static final String QUEUE_NAME = "acum.queue";
    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    Micro1Dao dao;

    public String getMicroserviceName()
    {
        return dao.getMicroserviceName();
    }
    public void sendEmailQueue(String message)  {
        try {
            MyMessage myMessage = new MyMessage();

            if(Objects.isNull(message) || message.equals(""))
                message  = "Hello";
            myMessage.setContent(message);
            rabbitTemplate.convertAndSend("acum.queue", myMessage);
            log.info("Sent: " + message);
        }
        catch (Exception e){
            log.error(e.getMessage());
        }
    }
}