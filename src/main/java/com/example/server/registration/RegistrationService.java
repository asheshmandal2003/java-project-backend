package com.example.server.registration;

import com.example.server.jwt.JwtService;
import com.example.server.response.ResponseHandler;
import com.example.server.security.PasswordEncoder;
import com.example.server.user.User;
import com.example.server.user.UserRepository;
import com.example.server.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
public class RegistrationService {

    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserRepository userRepository;

    private JwtService service;

    private UserService userService;

    public ResponseEntity<Object> register(User user) {
        if ( userRepository.findByEmail(
                user.getEmail()).isPresent()
        ){
            return ResponseHandler
                    .generateResponse(
                            HttpStatus.BAD_REQUEST,
                    false,
                    "Email already exists!",
                    null
            );
        }
        user.setPassword(PasswordEncoder.bCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return ResponseHandler
                .generateResponse(
                        HttpStatus.CREATED,
                        true,
                        "You're successfully registered!",
                        user.getId()
                );
    }

    public ResponseEntity<Object> login(AuthRequest authRequest){

        try {
//            UserDetails user = userService.loadUserByUsername(authRequest.getEmail());

            var authToken = new
                    UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), authRequest.getPassword()
            );

            Authentication authentication = authenticationManager.authenticate(authToken);

            if (authentication.isAuthenticated()) {
                String generatedToken = service.GenerateToken(
                        ((UserDetails) (authentication.getPrincipal())).getUsername()
                );

                User user = userRepository.findUserByEmail(authRequest.getEmail());

                Map<String, Object> res = new HashMap<>();
                res.put("token", generatedToken);
                res.put("user", user);
                return ResponseHandler.generateResponse(
                        HttpStatus.OK,
                        true,
                        "Login successful!",
                        res
                );
            }
            return ResponseHandler
                    .generateResponse(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            false,
                            "Something went wrong!",
                            null
                    );
        }catch (UsernameNotFoundException e){
            return ResponseHandler
                    .generateResponse(
                            HttpStatus.UNAUTHORIZED,
                            false,
                            e.getMessage(),
                            null
                    );
        }
    }
}

