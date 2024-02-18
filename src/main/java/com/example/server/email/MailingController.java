package com.example.server.email;

import com.example.server.response.ResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/forgot-password")
@AllArgsConstructor

public class MailingController {

    private MailingService mailingService;

    @PostMapping
    public ResponseEntity<Object> sendEmail(@RequestParam String email){
        try {
            mailingService.sendMessage(email);
            return ResponseHandler
                    .generateResponse(
                            HttpStatus.OK,
                            true,
                            "Email sent successfully!",
                            null
                    );
        }catch (Exception e){
            return ResponseHandler
                    .generateResponse(
                            HttpStatus.BAD_REQUEST,
                            false,
                            e.getMessage(),
                            null
                    );
        }
    }
}
