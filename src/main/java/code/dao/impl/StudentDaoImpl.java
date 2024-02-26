package code.dao.impl;

import code.dao.StudentDao;
import code.entity.Student;
import code.utils.JPAUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Component
public class StudentDaoImpl implements StudentDao {

    @Override
    public Student createStudent(Student student) {
       EntityManager em = null;
       try{
           em = JPAUtil.getEntityManagerFactory().createEntityManager();
           em.getTransaction().begin();
           em.persist(student);
           em.getTransaction().commit();
       }finally {
           if(em != null && em.isOpen()){
               em.close();
           }
       }
       return student;
    }

    @Override
    public Student findById(Long studentId) {
        EntityManager em = null;
        Student student;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            student = em.find(Student.class,studentId);
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return student;
    }

    @Override
    public List<Student> findByName(String name) {
        EntityManager em = null;
        List<Student> students;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<Student> query = em.createQuery("SELECT s FROM Student s WHERE s.name = :name ",Student.class);
            query.setParameter("name",name);
            students = query.getResultList();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return students;
    }

    @Override
    public List<Student> findAllStudent() {
        EntityManager em = null;
        List<Student> students;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            students = em.createQuery("SELECT s FROM Student s").getResultList();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return students;
    }

    @Override
    public Student updateStudent(Student student) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();

            Student updateStudent = em.find(Student.class,student.getId());

            if(updateStudent != null){
                updateStudent.setName(student.getName());
                updateStudent.setDob(student.getDob());
                updateStudent.setGender(student.getGender());
                updateStudent.setEducation(student.getEducation());
                updateStudent.setPhone(student.getPhone());
                Student updatedStudent = em.merge(updateStudent);
                em.getTransaction().commit();
                return updatedStudent;
            }else{
                em.getTransaction().rollback();
                return null;
            }


        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }

    @Override
    public boolean deleteStudent(Long studentId) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Student student = em.find(Student.class,studentId);
            if (student != null) {
                em.remove(student);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }
}
