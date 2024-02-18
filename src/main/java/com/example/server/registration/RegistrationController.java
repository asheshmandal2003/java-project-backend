package com.example.server.registration;

import com.example.server.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService;

    @PostMapping("/signup")
    public ResponseEntity<Object> register(@RequestBody User user){
        return registrationService.register(user);
    }

    @PostMapping("/signin")
    public ResponseEntity<Object> login(@RequestBody AuthRequest authRequest){
        return registrationService.login(authRequest);
    }
}