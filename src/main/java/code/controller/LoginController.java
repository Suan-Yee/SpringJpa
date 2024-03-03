package code.controller;

import code.service.EmailService;
import code.service.OTPService;
import code.service.UserDao;
import code.entity.OTP;
import code.entity.User;
import code.helper.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final UserDao userDao;
    private final EmailService emailService;
    private final OTPService otpService;

    @GetMapping("/")
    public ModelAndView loginUser(){
        return new ModelAndView("user/login","bean",new User());
    }
    @GetMapping("/otp")
    public ModelAndView enterOtp(){
        return new ModelAndView("user/confirmOTP","otp",new OTP());
    }
    @PostMapping("/confirm")
    public String confirmOTP(@ModelAttribute("otp") OTP otp ,HttpServletRequest request,Model model){
        HttpSession session = request.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("valid_user");
            OTP userOTP = otpService.findByUserId(user.getId());
            String code  = userOTP.getOtpCode();

                if (otp.getOtpCode().equals(code)) {
                    otpService.deleteOtp(user.getId());
                    return "redirect:/welcome";
                }else{
                    model.addAttribute("error","Check Your code again!");
                    model.addAttribute("gmail","use your actual gmail account!");
                    return "user/confirmOTP";
                }
        }
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
    @PostMapping("/login")
    public String login(@ModelAttribute("bean") @Validated User user, ModelMap model, HttpSession session){

        if(user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null || user.getPassword().isEmpty()){
            model.addAttribute("error","Please Fill the require fields");
            return "user/login";
        }

        User loginUser = userDao.loginUser(user);

        if (loginUser == null) {
            model.addAttribute("error", "Wrong Credentials");
            return "user/login";
        }else{
            session.setAttribute("valid_user",loginUser);
           /* otpService.deleteOtp(loginUser.getId());
            String code = otpService.generateOtp(loginUser);
            System.out.println(code);
            emailService.sendEmail(loginUser.getEmail(),code);*/

            return "redirect:/welcome";
        }

    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
    @GetMapping("/changePassword")
    public ModelAndView showEmailModel(){

        return new ModelAndView("user/changePassword","user",new User());
    }
    @PostMapping ("/checkEmail")
    public String checkEmail(@ModelAttribute("user")User user, Model model){
        User users = userDao.loginUser(user);

        if(users == null){
            model.addAttribute("error","Wrong Credentials");
            return "user/changePassword";
        }else{
            return "redirect:/changeEmail";
        }
    }
    @GetMapping("/changeEmail")
    public ModelAndView changeEmail(){

        ModelAndView view = new ModelAndView("user/changePassword");
        view.addObject("user",new User());
        return view;
    }
    @GetMapping("/change")
    public ModelAndView change(){

        ModelAndView view = new ModelAndView("user/change");
        view.addObject("user",new User());

        return view;
    }
    @PostMapping("/changeEmailAction")
    public String changeAction(@RequestParam(name = "id",required = false)Long userId,@ModelAttribute("user")User user,Model model,HttpServletRequest request){

        System.out.println("reached 1");
        HttpSession session = request.getSession(false);
        System.out.println("reached 2");
        User log_user = (User)session.getAttribute("valid_user");
        if(!user.getEmail().equalsIgnoreCase(log_user.getEmail()) || !user.getPassword().equalsIgnoreCase(log_user.getPassword())){
            model.addAttribute("error","check your email and password to match with this current account");
            return "user/changePassword";
        }
        return "redirect:/change";
    }

    @PostMapping("/changeEmailAndPassword")
    public String changeEmailAndPassword(@ModelAttribute("user")User user,Model model,HttpServletRequest request){

        HttpSession session = request.getSession(false);
        User log_user = (User)session.getAttribute("valid_user");
        user.setId(log_user.getId());
        if(userDao.isEmailUsed(user.getEmail()) > 0){
            model.addAttribute("error","Email already used");
            return "user/change";
        }
        if(!Validator.isValidPassword(user.getPassword())){
            model.addAttribute("error","Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
            return "user/change";
        }
        String confirmPass = request.getParameter("confirmPass");
        if(!user.getPassword().equals(confirmPass)){
            model.addAttribute("error","Password does not match");
            return "user/user_reg";
        }
        userDao.changeEmail(user,request);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";

    }
    @GetMapping("/welcome")
    public String welcome() {
        return "user/welcome";
    }
}
