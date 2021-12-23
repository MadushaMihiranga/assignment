package com.treinetic.assignment.emailsender;

import com.treinetic.assignment.message.EmailDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SMTPEmailService implements EmailService{

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendMail(EmailDTO email) {
        SimpleMailMessage message = new SimpleMailMessage();
        //message.setTo(emailDTO.getToEmail());
        message.setTo("madushamihiranga@outlook.com");
        message.setText(email.getBody());
        message.setSubject(email.getSubject());
        message.setFrom("testm6225@gmail.com");
        mailSender.send(message);
        log.info("Sent an email to : {}", (Object) message.getTo());
    }
}
