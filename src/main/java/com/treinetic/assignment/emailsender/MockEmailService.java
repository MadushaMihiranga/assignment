package com.treinetic.assignment.emailsender;

import com.treinetic.assignment.message.EmailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MockEmailService implements EmailService{
    @Override
    public void sendMail(EmailDTO email) {
        log.info("Sending an email to the : {}",email.getToEmail());
    }
}
