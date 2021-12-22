package com.treinetic.assignment.message;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private RabbitTemplate template;

    public CustomMessage publishMessage(CustomMessage message){
        message.setMessageId(UUID.randomUUID().toString());
        message.setMessageDate(new Date());
        template.convertAndSend(MQconfig.EXCHANGE,MQconfig.ROUTING_KEY,message);
        log.info("message published : {}",message.getMessage());
        return message;
    }

}
