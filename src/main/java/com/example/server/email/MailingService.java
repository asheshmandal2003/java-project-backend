package com.example.server.email;

import com.example.server.user.User;
import com.example.server.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MailingService{
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private final UserRepository userRepository;

    public void sendMessage(String email)
            throws UsernameNotFoundException, NoSuchAlgorithmException {
        Optional<User> isUser = userRepository.findByEmail(email);

        User user = isUser.map(User::new)
                .orElseThrow(()-> new UsernameNotFoundException(
                        "User with email " + email + " not found!"
                ));
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("noreply@myapp.com");
            simpleMailMessage.setTo(email);
            simpleMailMessage.setSubject("Forgot Password");
            simpleMailMessage.setText(user.getPassword());
            javaMailSender.send(simpleMailMessage);
    }
}

