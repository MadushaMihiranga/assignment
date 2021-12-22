package com.treinetic.assignment.service;

import com.treinetic.assignment.dto.StudentDTO;

import com.treinetic.assignment.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


public interface UserService {

    User getUser(String username);
    Page<User> findAll(int page,int size);
    User create(StudentDTO studentDTO);
    User updateFirstLastName(String id,StudentDTO studentDTO);
    User approveUser(String id,User newUser);
}
