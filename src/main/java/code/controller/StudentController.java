package code.controller;

import code.dao.CourseDao;
import code.dao.EnrollDao;
import code.dao.StudentDao;
import code.entity.Course;
import code.entity.RegisterForm;
import code.entity.Student;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudentController {

    private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final EnrollDao enrollDao;

    @GetMapping("/studentRegister")
    public ModelAndView studentRegister(){

        List<Course> courses = courseDao.selectByStatus();

        ModelAndView modelAndView = new ModelAndView("student/studentRegister");
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("register", new RegisterForm());

        return modelAndView;
    }
    @PostMapping("/addStudent")
    public String student_detail(@ModelAttribute("register") RegisterForm rgForm, Model model){


        List<Long> course_idList  = rgForm.getCourses();
        List<Course> courseList = new ArrayList<>();

        for(Long courses : course_idList){
            Course course = courseDao.findById(courses);
            courseList.add(course);
        }
        Student student = rgForm.getStudent();
        Student add_student = studentDao.createStudent(student);
        System.out.println(add_student.getName() + add_student.getDob());

        for(Course course : courseList){
            enrollDao.saveEnrollment(add_student,course);
        }
        List<Student> allStudents  = studentDao.findAllStudent();

        model.addAttribute("students",allStudents);

        return "student/student_details";
    }
    @GetMapping("/addStudent")
    public String showData(Model model){

        List<Student> allStudents  = studentDao.findAllStudent();
        model.addAttribute("students",allStudents);

        return "student/student_details";
    }
    @GetMapping("/updateStudent")
    public ModelAndView studentUpdate(@RequestParam("studentId") Long studentId) {

        Student d_student = studentDao.findById(studentId);
        List<Long> listOfCourse  = enrollDao.findCourseByStudentId(d_student.getId());
        System.out.println(listOfCourse);
        List<String> course_name = new ArrayList<>();
        for(Long i : listOfCourse){
            Course course  = courseDao.findById(i);
            course_name.add(course.getName());
        }
        System.out.println("Course name " + course_name);
        List<Course> courses = courseDao.selectByStatus();

        ModelAndView modelAndView = new ModelAndView("student/STU002-01");
        modelAndView.addObject("c_name",course_name);
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("student", d_student);
        modelAndView.addObject("register", new RegisterForm());

        return modelAndView;
    }
    @PostMapping("/updateStudentAction")
    public String updateStudentAction(@ModelAttribute("register")RegisterForm reg, Model model, HttpServletRequest request){

        String gender = request.getParameter("gender");
        String education = request.getParameter("education");
        String[] select_course = request.getParameterValues("course");

        String str_id  = request.getParameter("hide");
        Long id = Long.valueOf(0);
        if (str_id != null && !str_id.isEmpty()) {

            try {
                id = Long.parseLong(str_id);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid value for id: " + str_id);
            }
        }
        List<Long> course_id = new ArrayList<>();

        List<Course> courses = courseDao.selectByStatus();
        for(Course course : courses){
            if(Arrays.asList(select_course).contains(course.getName())){
                course_id.add(course.getId());
            }
        }

        Student student = reg.getStudent();
        student.setId(id);
        student.setGender(gender);
        student.setEducation(education);
        Student add_student = studentDao.updateStudent(student);
        enrollDao.deleteCourseAndStudent(add_student.getId());
        List<Course> courseList = new ArrayList<>();
        for(Long cur: course_id){
            Course course = courseDao.findById(cur);
            courseList.add(course);
        }
        for(Course course : courseList){
            enrollDao.saveEnrollment(add_student,course);
        }
        List<Student> allStudents  = studentDao.findAllStudent();

        model.addAttribute("students",allStudents);

        return "student/student_details";

    }
    @GetMapping("/deleteStudent")
    public String deleteUser(@RequestParam("studentId")Long studentId, RedirectAttributes redirectAttributes){
        enrollDao.deleteCourseAndStudent(studentId);
        studentDao.deleteStudent(studentId);
        redirectAttributes.addFlashAttribute("deletedUserId", studentId);
        return "redirect:/studentList";
    }
    @GetMapping("/studentList")
    public String AfterDelete(Model model){
        List<Student> students;
        students = studentDao.findAllStudent();
        model.addAttribute("students", students);
        return "student/student_details";
    }
    @GetMapping("/searchStudent")
    public String searchStudent(@RequestParam(name = "id", required = false) Long studentId,
                                @RequestParam(name = "name", required = false) String studentName,
                                @RequestParam(name = "course", required = false) String studentCourse,
                                Model model) {

        if (studentId != null) {
            Student student = studentDao.findById(studentId);
            model.addAttribute("students", Collections.singletonList(student));
        } else if (studentName != null && !studentName.isEmpty()) {
            List<Student> students = studentDao.findByName(studentName);
            model.addAttribute("students", students);
        } /*lse if (studentCourse != null && !studentCourse.isEmpty()) {
            List<Student> students = studentDao.findByCourse(studentCourse);
            model.addAttribute("students", students);
        }*/ else {
            List<Student> allStudents = studentDao.findAllStudent();
            model.addAttribute("students", allStudents);
        }

        return "student/student_details";
    }


}
