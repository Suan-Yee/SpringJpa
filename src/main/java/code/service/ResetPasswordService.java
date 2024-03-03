package code.service;

import code.entity.ResetPassword;
import code.entity.User;

public interface ResetPasswordService {

    String createUrl(User user);
    boolean deleteUrl(User user);
    ResetPassword findByUrl(String url);
    void expireResetPassword(ResetPassword resetPassword);
    boolean isExpired(ResetPassword resetPassword);
}
