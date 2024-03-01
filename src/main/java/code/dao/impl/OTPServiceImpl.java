package code.dao.impl;

import code.dao.OTPService;
import code.entity.OTP;
import code.entity.User;
import code.utils.JPAUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OTPServiceImpl implements OTPService {

    @Override
    public String generateOtp(User user) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            OTP otp = new OTP();
            otp.setUser(user);
            otp.setOtpCode(RandomStringUtils.randomAlphabetic(6).toUpperCase());
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            otp.setExpirationDate(calendar.getTime());
            em.persist(otp);
            em.getTransaction().commit();
            return otp.getOtpCode();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }

    @Override
    public boolean deleteOtp(Long userId) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();

            Query query = em.createQuery("DELETE FROM OTP o WHERE o.user.id = :id");
            query.setParameter("id", userId);

            int result = query.executeUpdate();
            em.getTransaction().commit();

            return result > 0;
        } catch (Exception e) {
            if (em != null && em.isOpen()) {
                em.getTransaction().rollback();
            }
            return false;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


    @Override
    public boolean isExpire(Long userId) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            TypedQuery<OTP> query = em.createQuery("SELECT o FROM OTP o WHERE o.user.id = :id",OTP.class);
            query.setParameter("id",userId);
            List<OTP> otps = query.getResultList();
            for (OTP otp : otps) {
                if (otp.getExpirationDate().before(new Date())) {
                    em.remove(otp);
                    return true;
                }
            }
            return false;
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }

    @Override
    public OTP findByUserId(Long userId) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<OTP> query = em.createQuery("SELECT o FROM OTP o WHERE o.user.id = :id",OTP.class);
            query.setParameter("id",userId);
            OTP otp = query.getSingleResult();
            return otp;
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }
}
