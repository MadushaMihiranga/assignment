package com.treinetic.assignment.message;

import com.treinetic.assignment.emailsender.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class MessageListener {

    private EmailService service;

    //@RabbitListener(queues = MQconfig.QUEUE)
    @RabbitListener(queuesToDeclare = @Queue(name = MQconfig.QUEUE))
    public void listener(CustomMessage message){
        //System.out.println(message);
        System.out.println(message.getMessage().getToEmail());
        service.sendMail(message.getMessage());

    }

}
