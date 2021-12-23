package com.treinetic.assignment.emailsender;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Objects;

@Slf4j
@Configuration
public class EmailConfig {

    @Value("${email.service}")
    String emailServiceType;

    @Bean
    @Primary
    public EmailService emailService(){
        if (Objects.equals(this.emailServiceType, "smtp")){
            return new SMTPEmailService();
        }else if (Objects.equals(this.emailServiceType, "mock")){
            return new MockEmailService();
        }else {
            log.error("Invalid Email Service: {}",emailServiceType);
        }
        return null;
    }

}
