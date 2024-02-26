package code.dao.impl;

import code.dao.CourseDao;
import code.entity.Course;
import code.utils.JPAUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
@Component
public class CourseDaoImpl implements CourseDao {

    @Override
    public Course createCourse(Course course) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.persist(course);
            em.getTransaction().commit();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return null;
    }

    @Override
    public Course findById(Long courseId) {
        EntityManager em = null;
        Course course;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            course = em.find(Course.class,courseId);
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return course;
    }

    @Override
    public Course findByName(String name) {
        EntityManager em = null;
        Course course;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c WHERE c.name = :name",Course.class);
            query.setParameter("name",name);
            course = query.getSingleResult();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return course;
    }

    @Override
    public boolean deleteCourse(Long courseId) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Course course = em.find(Course.class,courseId);
            if (course != null) {
                em.remove(course);
                return true;
            }else{
                em.getTransaction().rollback();
                return false;
            }
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }

    @Override
    public Course updateCourse(Course course) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Course updatedCourse = em.find(Course.class,course.getId());
            if(updatedCourse != null){
                updatedCourse.setName(course.getName());
                updatedCourse.setDescription(course.getDescription());
                updatedCourse.setInstructor(course.getInstructor());
                updatedCourse.setStatus(course.getStatus());
                Course updateCourse = em.merge(updatedCourse);
                em.getTransaction().commit();
                return updateCourse;
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
    public List<Course> selectByStatus() {
        EntityManager em = null;
        List<Course> courses;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c WHERE c.status = :status",Course.class);
            query.setParameter("status","publish");
            courses = query.getResultList();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return courses;
    }

    @Override
    public List<Course> findAllCourse() {
        EntityManager em = null;
        List<Course> courses;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            courses = em.createQuery("SELECT c FROM Course c").getResultList();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return courses;
    }

    @Override
    public List<Course> findByIdOrName(Long courseId, String name) {
        EntityManager em = null;
        List<Course> courses;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<Course> query = em.createQuery("SELECT c FROM Course c where c.id = :id OR c.name = :name",Course.class);
            query.setParameter("id",courseId);
            query.setParameter("name",name);
            courses = query.getResultList();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return courses;
    }

    @Override
    public Course changeStatus(Long courseId, Course course) {
        EntityManager em = null;
        Course updateCourse;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            updateCourse = em.find(Course.class,courseId);
            if(updateCourse != null) {
                if (course.getStatus().equalsIgnoreCase("pending")) {
                    updateCourse.setStatus("publish");
                } else {
                    updateCourse.setStatus("pending");
                }
                em.getTransaction().commit();
                return updateCourse;
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
}
