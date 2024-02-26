package code.dao;

import code.entity.Course;

import java.util.List;

public interface CourseDao {

     Course createCourse(Course course);
     Course findById(Long courseId);
     Course findByName(String name);
     boolean deleteCourse(Long courseId);
     Course updateCourse(Course course);
     List<Course> selectByStatus();
     List<Course> findAllCourse();
     List<Course> findByIdOrName(Long courseId,String name);
     Course changeStatus(Long courseId,Course course);
}
