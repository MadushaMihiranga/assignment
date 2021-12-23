package com.treinetic.assignment.user.service;

import com.treinetic.assignment.user.dto.StudentDTO;

import com.treinetic.assignment.user.entity.User;
import org.springframework.data.domain.Page;


public interface UserService {

    User getUser(String username);
    Page<User> findAll(int page,int size);
    User create(StudentDTO studentDTO);
    User updateFirstLastName(String id,StudentDTO studentDTO);
    User approveUser(String id,User newUser);
}
