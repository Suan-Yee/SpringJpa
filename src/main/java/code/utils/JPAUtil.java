package code.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

    static EntityManagerFactory emf = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf = Persistence.createEntityManagerFactory("hello");
    }

}
