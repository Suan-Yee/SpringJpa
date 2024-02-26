package code.dao;

import code.entity.Course;
import code.entity.Student;

import java.util.List;

public interface EnrollDao {

    boolean saveEnrollment(Student student, Course course);
    List<Long> findCourseByStudentId(Long studentId);
    boolean deleteCourseAndStudent(Long studentId);
}
