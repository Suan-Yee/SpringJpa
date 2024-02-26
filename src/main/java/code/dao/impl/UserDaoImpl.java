package code.dao.impl;

import code.dao.UserDao;
import code.entity.User;
import code.utils.JPAUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
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

        return null;
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
}