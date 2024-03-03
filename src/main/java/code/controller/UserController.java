package code.controller;

import code.service.UserDao;
import code.entity.User;
import code.helper.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserDao userDao;

    @GetMapping("/addUser")
    public String displayUser(Model model){
        List<User> allUsers =  userDao.findAll();
        model.addAttribute("users",allUsers);
        return "user/user_details";
    }

    @GetMapping("/userRegister")
    public ModelAndView userRegister(){
        return new ModelAndView("user/user_reg","user",new User());
    }
    @PostMapping("/addUser")
    public String addUser(@ModelAttribute("user") User user, Model model, HttpServletRequest request){

        if(user.getUsername() == null || user.getUsername().isEmpty() || user.getEmail() == null || user.getEmail().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()){
            model.addAttribute("error","Fields cannot be null");
            return "user/user_reg";
        }

        if(userDao.isEmailUsed(user.getEmail()) > 0){
            model.addAttribute("error","Email already used");
            return "user/user_reg";
        }
        String confirmPass = request.getParameter("confirmPass");
        if(!Validator.isValidPassword(user.getPassword())){
            model.addAttribute("error","Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit.");
            return "user/user_reg";
        }
        if(!user.getPassword().equals(confirmPass)){
            model.addAttribute("error","Password does not match");
            return "user/user_reg";
        }
        HttpSession session = request.getSession(false);
        User log_user = (User) session.getAttribute("valid_user");

        if(log_user != null) {
            userDao.createUser(user);
            List<User> users = userDao.findAll();
            model.addAttribute("users", users);
            return "user/user_details";
        }else{
            userDao.createUser(user);
            return "redirect:/";
        }

    }
    @GetMapping("/updateUser")
    public ModelAndView userUpdate(@RequestParam("userId") Long userId){

        User user = userDao.findById(userId);
        ModelAndView modelAndView = new ModelAndView("user/user_update");
        modelAndView.addObject("user", user);
        modelAndView.addObject("userObj", new User());

        return modelAndView;
    }
    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId")Long userId, RedirectAttributes redirectAttributes, HttpServletRequest request){

        User user = (User) request.getSession().getAttribute("valid_user");
        User searchUser = userDao.findById(userId);

        if(user != null && user.getEmail().equals(searchUser.getEmail())){
            userDao.deleteUser(userId);
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            return "redirect:/";
        }else{
            userDao.deleteUser(userId);
            redirectAttributes.addFlashAttribute("deletedUserId", userId);
            return "redirect:/userList";
        }

    }
    @GetMapping("/userList")
    public String AfterDelete(Model model){
        List<User> users;
        users = userDao.findAll();
        model.addAttribute("users", users);
        return "user/user_details";
    }
    @GetMapping("/searchUser")
    public String searchUser(@RequestParam(name = "id", required = false) Long userId,
                             @RequestParam(name = "name", required = false) String userName,
                             Model model) {

        List<User> searchResults;
        if(userId != null || (userName != null && !userName.isEmpty())){

            searchResults = userDao.findByIdOrUserName(userId, userName);
            if(searchResults.isEmpty()){
                model.addAttribute("errors","There is no user found");
            }
            model.addAttribute("users", searchResults);
        }else{
            List<User> users = userDao.findAll();
            model.addAttribute("users", users);
        }

        return "user/user_details";

    }
}
