package org.analiseGenoma.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Cromossomo;
import org.analiseGenoma.model.User;

public class UserDao extends DAO<User> {

    public UserDao() {
        super(User.class);
    }

    public List<User> findReviser(String email, String password) {
        try {
            Query q = manager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password AND u.reviser = true");
            q.setParameter("email", email);
            q.setParameter("password", password);
            q.setHint("org.hibernate.cacheable", true);
            //q.setCacheable(true);
            return q.getResultList();

        } catch (NoResultException ex) {
            System.out.println("UserDao.findReviser erro:" + ex.getMessage());
        }
        return new ArrayList<>();
    }

    
    public List<User> findManager(String email, String password) {
        try {
            Query q = manager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password AND u.manager = true");
            q.setParameter("email", email);
            q.setParameter("password", password);
            q.setHint("org.hibernate.cacheable", true);
            //q.setCacheable(true);
            return q.getResultList();

        } catch (NoResultException ex) {
            System.out.println("UserDao.findReviser erro:" + ex.getMessage());
        }
        return new ArrayList<>();
    }
    
        public List<User> findAdministrator(String email, String password) {
        try {
            Query q = manager.createQuery("SELECT u FROM User u WHERE u.email = :email AND u.password = :password AND u.administrator = true");
            q.setParameter("email", email);
            q.setParameter("password", password);
            q.setHint("org.hibernate.cacheable", true);
            //q.setCacheable(true);
            return q.getResultList();

        } catch (NoResultException ex) {
            System.out.println("UserDao.findReviser erro:" + ex.getMessage());
        }
        return new ArrayList<>();
    }
    
    
    
//    @Override
//    public List<User> find() {
//        return manager.createQuery("Select u from User").getResultList();
//    }
}
