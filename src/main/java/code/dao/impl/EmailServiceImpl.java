package code.dao.impl;

import code.dao.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to) {

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("User Account Verification");
            message.setFrom("kosuanyeeaung44250@gmail.com");
            message.setTo(to);
            message.setText("Hay this is working");
            javaMailSender.send(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
