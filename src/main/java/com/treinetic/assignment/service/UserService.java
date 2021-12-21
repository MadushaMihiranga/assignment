package com.treinetic.assignment.service;

import com.treinetic.assignment.dto.StudentDTO;
import com.treinetic.assignment.entity.Role;
import com.treinetic.assignment.entity.User;
import com.treinetic.assignment.repository.RoleRepository;
import com.treinetic.assignment.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    //private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User getUser(String username){
        return userRepository.findByUsername(username);
    }

    public Page<User> findAll(int page,int size){
        return userRepository.findAll(PageRequest.of(page, size));
    }

    public User create(StudentDTO studentDTO){
        User user = new User();
        List<Role> roles= new ArrayList<>();
        user.setFirstname(studentDTO.getFirstname());
        user.setLastname(studentDTO.getLastname());
        user.setEmail(studentDTO.getEmail());
        user.setPassword(studentDTO.getPhonenumber());
        user.setApproved(false);
        user.setEmailVerified(false);
        user.setCreatedAt(LocalDate.now());
        user.setUpdatedAt(LocalDate.now());
       // user.setPassword();
        roles.add(roleRepository.findRoleByName("Student").get());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User updateFirstLastName(String id,StudentDTO studentDTO){
        Optional<User> oldUser = userRepository.findById(id);
        if (oldUser.isPresent()){
            User newUser = oldUser.get();
            newUser.setFirstname(studentDTO.getFirstname());
            newUser.setLastname(studentDTO.getLastname());
            return userRepository.save(newUser);
        }else {
            return null;
        }
    }

    public User approveUser(String id,User newUser){
        Optional<User> oldUser = userRepository.findById(id);
        if (oldUser.isPresent()){
            User user = oldUser.get();
            user.setApproved(true);
            return userRepository.save(user);
        }else {
            return null;
        }
    }



}
