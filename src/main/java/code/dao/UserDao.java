package code.dao;

import code.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserDao {

    User createUser(User user);
    User findById(Long id);
    User loginUser(User user);
    User changeEmail(User user, HttpServletRequest request);
    List<User> findByName(String name);
    boolean deleteUser(Long userId);
    List<User> findAll();
    List<User> findByIdOrUserName(Long userId,String name);
    Integer validEmail(String email);

}
