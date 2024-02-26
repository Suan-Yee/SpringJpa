package code.dao.impl;

import code.dao.InstructorDao;
import code.entity.Instructor;
import code.utils.JPAUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Service
@Component
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

        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Instructor> query = em.createQuery("SELECT i FROM Instructor i WHERE i.name LIKE :name",Instructor.class);
            query.setParameter("name", "%" + name + "%");
            Instructor instructor = query.getSingleResult();

            return instructor;
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }
}
