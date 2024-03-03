package code.service;


public interface EmailService {

    void sendEmail(String to,String code,String message);
    void sendUrlToEmail(String to,String code,String text);
}
