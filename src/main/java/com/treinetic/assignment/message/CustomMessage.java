package com.treinetic.assignment.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomMessage {

    private String messageId;
    private EmailDTO message;
    private Date messageDate;

}
