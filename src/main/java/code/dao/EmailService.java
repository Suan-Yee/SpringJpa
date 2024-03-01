package code.dao;

import org.springframework.stereotype.Service;


public interface EmailService {

    void sendEmail(String to,String code);
}
