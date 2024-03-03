package code.service;

import code.entity.OTP;
import code.entity.User;

public interface OTPService {

    String generateOtp(User user);
    boolean deleteOtp(Long userId);
    boolean isExpire(Long userId);
    OTP findByUserId(Long userId);
}
