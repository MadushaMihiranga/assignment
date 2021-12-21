package com.treinetic.assignment.service;

import com.treinetic.assignment.entity.Role;
import com.treinetic.assignment.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> getRoleList(){
        return roleRepository.findAll();
    }

}
