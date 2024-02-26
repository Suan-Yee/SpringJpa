package code.dao;

import code.entity.Instructor;

public interface InstructorDao {

    Instructor create(Instructor instructor);
    Instructor findByName(String name);
}
