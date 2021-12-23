package com.treinetic.assignment.emailsender;

import com.treinetic.assignment.message.EmailDTO;

public interface EmailService {
    public void sendMail(EmailDTO email);
}
