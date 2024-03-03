package code.service.impl;

import code.service.InstructorDao;
import code.entity.Instructor;
import code.utils.JPAUtil;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Service
public class InstructorDaoImpl implements InstructorDao {


    @Override
    public Instructor create(Instructor instructor) {

        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            em.persist(instructor);
            em.getTransaction().commit();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return instructor;
    }

    @Override
    public Instructor findByName(String name) {
        EntityManager em = null;
        List<Instructor> instructor;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Instructor> query = em.createQuery("SELECT i FROM Instructor i WHERE i.name = :name", Instructor.class);
            query.setParameter("name",name);
            instructor = query.getResultList();

            if (instructor.isEmpty()) {
                return null;
            } else {
                return instructor.get(0);
            }
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
