package code.service.impl;

import code.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String to,String code,String text) {

        /*try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("User Account Verification");
            message.setFrom("kosuanyeeaung44250@gmail.com");
            message.setTo(to);
            message.setText(text + " " + code);
            javaMailSender.send(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }*/
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setSubject("User Account Verification");
            helper.setFrom("kosuanyeeaung44250@gmail.com");
            helper.setTo(to);

            String htmlContent = "<p>" + text + " " + code + "</p>";
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void sendUrlToEmail(String to,String code,String text) {

        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setSubject("Password Reset");
            helper.setFrom("kosuanyeeaung44250@gmail.com");
            helper.setTo(to);

            String link = "http://localhost:8080/resets/" + code;
            String htmlContent = "<p>" + text + " <a href='" + link + "'>Click here to reset your password</a></p>";
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
