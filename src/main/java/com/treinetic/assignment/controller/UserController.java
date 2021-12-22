package com.treinetic.assignment.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treinetic.assignment.dto.StudentDTO;
import com.treinetic.assignment.entity.Role;
import com.treinetic.assignment.entity.User;
import com.treinetic.assignment.service.RoleService;
import com.treinetic.assignment.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

    @GetMapping("/token/refresh")
    public void getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();

                User user = userService.getUser(username);

                String access_token = JWT.create()
                        .withSubject(user.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 100))
                        .withIssuer(request.getRequestURI().toString())
                        .withClaim("roles",user.getRoles().stream().map(Role:: getName).collect(Collectors.toList()))
                        .sign(algorithm);

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token",access_token);
                tokens.put("refresh_token",refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);


            }catch (Exception e){
                log.error("Error logged in: {}",e.getMessage());
                response.setHeader("error",e.getMessage());
                response.setStatus(FORBIDDEN.value());
                //response.sendError(FORBIDDEN.value());

                Map<String, String> error = new HashMap<>();
                error.put("error_message",e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);

            }
        }else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

}
