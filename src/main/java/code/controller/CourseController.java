package code.controller;

import code.dao.CourseDao;
import code.dao.InstructorDao;
import code.entity.Course;
import code.entity.CourseForm;
import code.entity.Instructor;
import com.mysql.cj.PreparedQuery;
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
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseDao courseDao;
    private final InstructorDao instructorDao;

   /* @GetMapping("/courseRegister")
    public ModelAndView register(){
        return new ModelAndView("course/course_reg","course",new Course());
    }*/

    @GetMapping("/courseRegister")
    public ModelAndView register(){
        return new ModelAndView("course/course_reg","courseForm",new CourseForm());
    }
    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute("courseForm")CourseForm courseForm, HttpServletRequest request){

        Course course = courseForm.getCourse();
        String instructor_name = courseForm.getInstructor();
        Instructor instructor = instructorDao.findByName(instructor_name);
        course.setInstructor(instructor);
        courseDao.createCourse(course);
        return "redirect:courseRegister";
    }


  /*  @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute("course")Course course, HttpServletRequest request){
        System.out.println("Hello from addCourse!");
        String instructor_name = request.getParameter("instructor");
        Instructor instructor = instructorDao.findByName(instructor_name);
        course.setInstructor(instructor);
        Course course1 = courseDao.createCourse(course);
        System.out.println(course1.getName());
        return "redirect:courseRegister";
    }*/
    @GetMapping("/courseDetails")
    public String courseDetails(Model model){
        List<Course> courses = courseDao.findAllCourse();
        System.out.println(courses);
        model.addAttribute("courses",courses);

        return "course/course_details";
    }
    @GetMapping("/searchCourse")
    public String searchCourse(@RequestParam(name = "id", required = false) Long courseId,
                               @RequestParam(name = "name", required = false) String courseName,
                               Model model){

        List<Course> courses;
        if(courseId != null  || (courseName != null && !courseName.isEmpty())){
            courses = courseDao.findByIdOrName(courseId, courseName);
            if(courses.isEmpty()){
                model.addAttribute("error","There is no course with search results");
            }
        }else{
            courses = courseDao.findAllCourse();
        }
        model.addAttribute("courses",courses);


        return "course/course_details";
    }

    @GetMapping("/courseDelete")
    public String courseDelete(@RequestParam(name = "courseId",required = false) Long courseId){

             courseDao.enableCourse(courseId);
        System.out.println("Course delete" );
        return "redirect:/courseDetails";
    }

    @GetMapping("/courseStatus")
    public String courseStatus(@RequestParam(name = "courseId",required = false)Long courseId, Model model, RedirectAttributes redirectAttributes){
        Course course = courseDao.findById(courseId);
        Course updated_course = courseDao.changeStatus(courseId,course);
        model.addAttribute("status",updated_course.getStatus());
        redirectAttributes.addFlashAttribute("courseId", courseId);
        return "redirect:/courseDetails";
    }
}
