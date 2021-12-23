package com.treinetic.assignment.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    private String id;
    private String firstname;
    private String lastname;

    @Email
    private String email;
    private String phoneNumber;
    private boolean isApproved;

    private boolean isEmailVerified;
    private String password;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private List<Role> roles;

}
