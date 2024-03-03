package code.service;

import code.entity.Student;

import java.util.List;

public interface StudentDao {

     Student createStudent(Student student);
     Student findById(Long studentId);
     List<Student> findByName(String name);
     List<Student> findAllStudent();
     Student updateStudent(Student student);
     boolean deleteStudent(Long studentId);
}
