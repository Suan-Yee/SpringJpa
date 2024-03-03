package code.service.impl;

import code.entity.ResetPassword;
import code.entity.User;
import code.service.ResetPasswordService;
import code.utils.JPAUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResetPasswordImpl implements ResetPasswordService {


    @Override
    public String createUrl(User user) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            ResetPassword resetPassword = new ResetPassword();
            resetPassword.setUrl(UUID.randomUUID().toString());
            resetPassword.setUser(user);

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            resetPassword.setExpirationDate(calendar.getTime());
            em.persist(resetPassword);
            em.getTransaction().commit();
            return resetPassword.getUrl();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }
    @Override
    public ResetPassword findByUrl(String url) {
        EntityManager em = null;
        ResetPassword result;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<ResetPassword> query = em.createQuery("SELECT r FROM ResetPassword r WHERE r.url = :url",ResetPassword.class);
            query.setParameter("url",url);
            result = query.getSingleResult();
            return result;
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }
    @Override
    public void expireResetPassword(ResetPassword resetPassword) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, -1);
        resetPassword.setExpirationDate(calendar.getTime());
    }
    @Override
    public boolean isExpired(ResetPassword resetPassword) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 1);
        return calendar.getTime().after(resetPassword.getExpirationDate());
    }

    @Override
    public boolean deleteUrl(User user) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Query query = em.createQuery("DELETE from ResetPassword r WHERE r.user.id = :id");
            query.setParameter("id",user.getId());
            int result = query.executeUpdate();
            em.getTransaction().commit();
            return result > 0;
        }catch (Exception e) {
            if (em != null && em.isOpen()) {
                em.getTransaction().rollback();
            }
            return false;
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }
}
