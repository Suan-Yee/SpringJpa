package code.service.impl;

import code.service.EnrollDao;
import code.entity.Course;
import code.entity.Enroll;
import code.entity.Student;
import code.utils.JPAUtil;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class EnrollDaoImpl implements EnrollDao {

    @Override
    public boolean saveEnrollment(Student student, Course course) {

        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Enroll enroll = new Enroll();
            enroll.setStudent(student);
            enroll.setCourse(course);
            enroll.setEnroll_date(LocalDateTime.now());
            em.persist(enroll);
            em.getTransaction().commit();
            return true;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Long> findCourseByStudentId(Long studentId) {

        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<Long> query = em.createQuery("SELECT e.course.id FROM Enroll e WHERE e.student.id = :studentId",Long.class);
            query.setParameter("studentId", studentId);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public boolean deleteCourseAndStudent(Long studentId) {

        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE FROM Enroll WHERE student.id = :studentId");
            query.setParameter("studentId", studentId);

            int deletedCount = query.executeUpdate();
            em.getTransaction().commit();
            return deletedCount > 0;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public List<Student> findByCourse(Long courseId) {
        EntityManager em = null;
        List<Student> students = Collections.emptyList();
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<Student> query = em.createQuery("SELECT e.student FROM Enroll e WHERE e.course.id = :courseId",Student.class);
            query.setParameter("courseId",courseId);

            students = query.getResultList();
            return students;
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }
}
