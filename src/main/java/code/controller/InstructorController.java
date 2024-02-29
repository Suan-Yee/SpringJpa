package code.controller;

import code.dao.InstructorDao;
import code.entity.Instructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class InstructorController {

    private final InstructorDao instructorDao;

    @GetMapping("instructorReg")
    public ModelAndView show(){

        return new ModelAndView("instructor/instructor_reg","instructor",new Instructor()) ;
    }
    @PostMapping("addInstructor")
    public String addInstructor(@ModelAttribute("instructor")Instructor instructor, Model model){
        instructorDao.create(instructor);
        return "user/welcome";
    }
}
