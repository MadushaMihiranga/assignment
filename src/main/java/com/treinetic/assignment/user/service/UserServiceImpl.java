package com.treinetic.assignment.user.service;

import com.treinetic.assignment.user.dto.StudentDTO;
import com.treinetic.assignment.user.entity.Role;
import com.treinetic.assignment.user.entity.User;
import com.treinetic.assignment.message.CustomMessage;
import com.treinetic.assignment.message.EmailDTO;
import com.treinetic.assignment.message.MessageService;
import com.treinetic.assignment.user.repository.RoleRepository;
import com.treinetic.assignment.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MessageService messageService;

    @Override
    public User getUser(String username){
        return userRepository.findByUsername(username).get();
    }

    @Override
    public Page<User> findAll(int page, int size){
        return userRepository.findAll(PageRequest.of(page, size));
    }

    @Override
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
        user.setUpdatedAt(null);
        user.setPassword(passwordEncoder.encode(studentDTO.getFirstname()));
        roles.add(roleRepository.findRoleByName("Student").get());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
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

    @Override
    public User approveUser(String id,User newUser){
        Optional<User> oldUser = userRepository.findById(id);
        if (oldUser.isPresent()){
            User user = oldUser.get();
            user.setApproved(true);
            User user1 = userRepository.save(user);
            EmailDTO emailDTO = new EmailDTO(
                    user.getEmail(),
                    "Account is approved",
                    "Dear "+user.getFirstname()+"\nYour Account is approved");
            CustomMessage message = new CustomMessage(null,emailDTO,null);
            messageService.publishMessage(message);
            return user1;
        }else {
            return null;
        }
    }

    // UserDetailsService
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()){
            log.error("User Not found in the database");
            throw new UsernameNotFoundException("User Not found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.get().getRoles().forEach( role -> { authorities.add(new SimpleGrantedAuthority(role.getName()));} );
        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),user.get().getPassword(),authorities
        );
    }




}
