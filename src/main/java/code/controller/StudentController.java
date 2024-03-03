package code.controller;

import code.service.CourseDao;
import code.service.EnrollDao;
import code.service.StudentDao;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


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
    public String student_detail(@ModelAttribute("register") RegisterForm rgForm, Model model,HttpServletRequest request){

        String image = saveImage(rgForm,request);
        List<Student> allStudents = saveStudent(rgForm,image);
        model.addAttribute("students",allStudents);

        return "student/student_details";
    }

    @GetMapping("/addStudent")
    public String showData(Model model) {

        List<Student> allStudents = studentDao.findAllStudent();
        model.addAttribute("students", allStudents);

        return "student/student_details";
    }

    @GetMapping("/updateStudent")
    public ModelAndView studentUpdate(@RequestParam("studentId") Long studentId) {

        Student d_student = studentDao.findById(studentId);
        List<Long> listOfCourse  = enrollDao.findCourseByStudentId(d_student.getId());
        List<String> course_name = new ArrayList<>();
        for(Long i : listOfCourse){
            Course course  = courseDao.findById(i);
            course_name.add(course.getName());
        }
        List<Course> courses = courseDao.selectByStatus();

        ModelAndView modelAndView = new ModelAndView("student/student_update");
        modelAndView.addObject("c_name",course_name);
        modelAndView.addObject("courses", courses);
        modelAndView.addObject("student", d_student);
        modelAndView.addObject("register", new RegisterForm());

        return modelAndView;
    }
    @PostMapping("/updateStudentAction")
    public String updateStudentAction(@ModelAttribute("register")RegisterForm reg,@RequestParam("hiddenId") Long studentId ,Model model, HttpServletRequest request){

        String gender = request.getParameter("gender");
        String education = request.getParameter("education");
        String[] select_course = request.getParameterValues("course");


        String image = saveImage(reg,request);
        deleteImage(studentId,request);
        String str_id  = request.getParameter("hide");
        Long id = 0L;
        if (str_id != null && !str_id.isEmpty()) {

            try {
                id = Long.parseLong(str_id);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid value for id: " + str_id);
            }
        }
        List<Long> course_id = null;
        if (select_course != null) {
            course_id = course_id(select_course);
        }
        updateStudent(reg,id,gender,education,image,course_id);
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
            if(student == null){
                model.addAttribute("error","There is no student found");
            }
            model.addAttribute("students", Collections.singletonList(student));
        } else if (studentName != null && !studentName.isEmpty()) {
            List<Student> students = studentDao.findByName(studentName);
            if(students.isEmpty()){
                model.addAttribute("error","There is no student found");
            }
            model.addAttribute("students", students);
        } else if (studentCourse != null && !studentCourse.isEmpty()) {
            Course course = courseDao.findByName(studentCourse);
            if(course == null){
                model.addAttribute("error","Unregister course");
            }else {
                Long course_id = course.getId();
                List<Student> students = enrollDao.findByCourse(course_id);
                if(students.isEmpty()){
                    model.addAttribute("error","There is not student register in given course");
                }
                model.addAttribute("students", students);
            }
        } else {
            List<Student> allStudents = studentDao.findAllStudent();
            model.addAttribute("students", allStudents);
        }

        return "student/student_details";
    }
    @GetMapping("studentDetails")
    public String displayView(@RequestParam(name = "studentId",required = false) Long id,Model model){
        Student student = studentDao.findById(id);
        List<Long> course_id = enrollDao.findCourseByStudentId(id);
        List<String> courses = new ArrayList<>();
        for(Long course : course_id){
            Course result = courseDao.findById(course);
            courses.add(result.getName());
        }
        model.addAttribute("student",student);
        model.addAttribute("course",courses);
        return "student/details";
    }

    private String saveImage(RegisterForm rgf, HttpServletRequest request) {
        MultipartFile file = rgf.getStudent().getFile();
        String originalFileName = file.getOriginalFilename();

        String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        Path path = Paths.get(rootDirectory + "WEB-INF/images/" + uniqueFileName);

        if (file != null && !file.isEmpty()) {
            try {
                file.transferTo(new File(path.toString()));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("File cannot be uploaded");
            }
        }
        return uniqueFileName;
    }
    public boolean deleteImage(Long studentId, HttpServletRequest request) {
        try {
            Student student = studentDao.findById(studentId);
            String rootDirectory = request.getSession().getServletContext().getRealPath("/");

            if (student != null) {
                String previousFileName = student.getImageUrl();
                if (previousFileName != null && !previousFileName.isEmpty()) {
                    Path previousPath = Paths.get(rootDirectory + "WEB-INF/images/" + previousFileName);
                    Files.deleteIfExists(previousPath);
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Student> saveStudent(RegisterForm rg,String image){

        List<Long> course_idList  = rg.getCourses();
        List<Course> courseList = new ArrayList<>();

        for(Long courses : course_idList){
            Course course = courseDao.findById(courses);
            courseList.add(course);
        }
        Student student = rg.getStudent();
        student.setImageUrl(image);
        Student add_student = studentDao.createStudent(student);

        for(Course course : courseList){
            enrollDao.saveEnrollment(add_student,course);
        }
        List<Student> allStudents  = studentDao.findAllStudent();

        return allStudents;
    }

    private List<Long> course_id(String[] select_course){

        List<Long> course_id = new ArrayList<>();

        List<Course> courses = courseDao.selectByStatus();
        for(Course course : courses){
            if(Arrays.asList(select_course).contains(course.getName())){
                course_id.add(course.getId());
            }
        }
        return course_id;
    }
    private void updateStudent(RegisterForm reg,Long id,String gender,String education,String image,List<Long> course_id){

        Student student = reg.getStudent();
        student.setId(id);
        student.setGender(gender);
        student.setEducation(education);
        student.setImageUrl(image);
        Student add_student = studentDao.updateStudent(student);
        enrollDao.deleteCourseAndStudent(add_student.getId());
        List<Course> courseList = new ArrayList<>();
        if(course_id != null){
            for(Long cur: course_id){
                Course course = courseDao.findById(cur);
                courseList.add(course);
            }
            for(Course course : courseList){
                enrollDao.saveEnrollment(add_student,course);
            }
        }
    }





}
