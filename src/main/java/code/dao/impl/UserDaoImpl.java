package code.dao.impl;

import code.dao.UserDao;
import code.entity.OTP;
import code.entity.Role;
import code.entity.User;
import code.utils.JPAUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
@Component
public class UserDaoImpl implements UserDao {

    @Override
    public User createUser(User user) {

        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            Role role = new Role();
            role.setName("USER");
            role.setUser(user);
            user.setRole(role);
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return user;
        }
    @Override
    public User findById(Long id) {
        EntityManager em = null;
        User user;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            user = em.find(User.class,id);
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return user;
    }

    @Override
    public User loginUser(User user) {
        EntityManager em = null;
        User result;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password",User.class);
            query.setParameter("email",user.getEmail());
            query.setParameter("password",user.getPassword());

            result = query.getSingleResult();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return result;
    }

    @Override
    public User changeEmail(User user, HttpServletRequest request) {
        EntityManager em = null;
        User updatedUser;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            User toUpdate = em.find(User.class,user.getId());
            if(toUpdate != null){
                toUpdate.setEmail(user.getEmail());
                toUpdate.setPassword(user.getPassword());
            }
            updatedUser = em.merge(toUpdate);
            em.getTransaction().commit();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return updatedUser;
    }

    @Override
    public List<User> findByName(String name) {
        EntityManager em = null;
        List<User> result;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();

            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.username = :name",User.class);
            query.setParameter("name",name);
            result = query.getResultList();

        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return result;
    }

    @Override
    public boolean deleteUser(Long userId) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            User deleteUser = em.find(User.class,userId);
            if (deleteUser != null) {
                em.remove(deleteUser);
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

    @Override
    public List<User> findAll() {
        EntityManager em = null;
        List<User> userList;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            userList = em.createQuery("SELECT u from User u").getResultList();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
        return userList;
    }

    @Override
    public List<User> findByIdOrUserName(Long userId, String name) {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<User> query;
            List<User> users;

            if (userId != null) {
                query = em.createQuery("SELECT u FROM User u WHERE u.id = :id", User.class);
                query.setParameter("id", userId);
            } else {
                query = em.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class);
                query.setParameter("name", name);
            }

            em.getTransaction().begin();
            users = query.getResultList();
            em.getTransaction().commit();

            if (users.isEmpty()) {
                return null;
            }

            return users;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Integer validEmail(String email) {
        EntityManager em = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            TypedQuery<Long> query = em.createQuery("SELECT COUNT(u.email) FROM User u WHERE u.email = :email",Long.class);
            query.setParameter("email",email);

            Long count = query.getSingleResult();
            em.getTransaction().commit();
            return count.intValue();
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }

    @Override
    public void generateOtp(User user) {
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

    @Override
    public String findRoleByUserId(Long userId) {
        EntityManager em = null;
        String name = null;
        try{
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r WHERE r.user.id = :userId", Role.class);
            Role role = query.getSingleResult();
            name = role.getName();
            return name;
        }finally {
            if(em != null && em.isOpen()){
                em.close();
            }
        }
    }


}
