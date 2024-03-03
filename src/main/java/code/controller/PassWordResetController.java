package code.controller;

import code.entity.ResetPassword;
import code.entity.User;
import code.helper.Validator;
import code.service.EmailService;
import code.service.ResetPasswordService;
import code.service.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class PassWordResetController {

    private final UserDao userDao;
    private final ResetPasswordService resetPasswordService;
    private final EmailService emailService;

    @GetMapping("/reset")
    public ModelAndView showForm() {
        return new ModelAndView("user/checkEmail","user",new User());
    }
    @PostMapping("/resets")
    public String submitForm(@ModelAttribute("user")User user, Model model) {

        User isValid = userDao.findByEmail(user.getEmail());
        if(isValid == null){
            model.addAttribute("error","There is no user found with that email");
            return "user/checkEmail";
        }
        resetPasswordService.deleteUrl(isValid);
        String url = resetPasswordService.createUrl(isValid);

        String message = "Please click this link to reset Password";
        emailService.sendUrlToEmail(user.getEmail(),url,message);
        return "user/email-send";
    }
    @GetMapping("/resets/{token}")
    public ModelAndView showResetForm(@PathVariable("token") String url, Model model) {
        ResetPassword resetPassword = resetPasswordService.findByUrl(url);
        if (resetPassword == null || resetPasswordService.isExpired(resetPassword)) {
            model.addAttribute("error", "Invalid or expired reset token");
            return new ModelAndView("user/checkEmail");
        }
        ModelAndView view = new ModelAndView("user/forgotPassword");
        User resultUser = resetPassword.getUser();
        view.addObject("result",resultUser);
        view.addObject("user",new User());
        return view;
    }
    @PostMapping("/resets/{token}")
    public String submitResetForm(@PathVariable("token") String url, @ModelAttribute("user")User user, Model model, HttpServletRequest request) {
        ResetPassword resetPassword = resetPasswordService.findByUrl(url);
        if (resetPassword == null || resetPasswordService.isExpired(resetPassword)) {
            model.addAttribute("error", "Invalid or expired reset token");
            return "user/checkEmail";
        }
        if(!Validator.isValidPassword(user.getPassword())){
            model.addAttribute("error","Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
            return "user/forgotPassword";
        }
        String confirmPass = request.getParameter("confirmPass");
        if(!user.getPassword().equals(confirmPass)){
            model.addAttribute("error","Password does not match");
            return "user/forgotPassword";
        }
        User update_user = resetPassword.getUser();
        userDao.updatePassword(update_user, user.getPassword());

        return "redirect:/";
    }
}
