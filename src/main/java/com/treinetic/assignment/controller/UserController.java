package com.treinetic.assignment.controller;

import com.treinetic.assignment.dto.StudentDTO;
import com.treinetic.assignment.entity.User;
import com.treinetic.assignment.service.RoleService;
import com.treinetic.assignment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@Tag(name = "User", description = "User API")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/roles")
    @Operation(summary = "Get role list")
    public ResponseEntity<?> getRoleList(){
        try {
            return new ResponseEntity<>(roleService.getRoleList(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    @Operation(summary = "Get user list")
    public ResponseEntity<?> fetchAllUsers(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ){
        try {
            return new ResponseEntity<>(userService.findAll(page, size), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    @Operation(summary = "Create user")
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO){
        try {
            return new ResponseEntity<>(userService.create(studentDTO),HttpStatus.CREATED);
        }catch (Exception e){
            log.error("Save new user to database : {}",e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/update/name/{id}")
    @Operation(summary = "Update First name and Last name of the student")
    public ResponseEntity<?> updateName(
            @PathVariable("id") String id,
            @RequestBody StudentDTO studentDTO
    ){
        try {
            return new ResponseEntity<>(userService.updateFirstLastName(id,studentDTO),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping(value = "/approve/{id}")
    @Operation(summary = "Approve the student")
    public ResponseEntity<?> approve(
            @PathVariable("id") String id,
            @RequestBody User user
    ){
        try {
            return new ResponseEntity<>(userService.approveUser(id,user),HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
