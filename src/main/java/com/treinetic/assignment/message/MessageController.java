package com.treinetic.assignment.message;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@Slf4j
@RestController
public class MessageController {

 /*   private final MessageService messageService;

    @PostMapping("/publish")
    public ResponseEntity<?> publish(@RequestBody CustomMessage message){
        try {
            return new ResponseEntity<>(messageService.publishMessage(message), HttpStatus.OK);
        }catch (Exception e){
            log.error("Failed to send message : {}",e.getMessage());
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
*/

}
