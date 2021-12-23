package com.treinetic.assignment.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor
public class EmailDTO {

    private String toEmail;
    private String subject;
    private String body;

}
