package org.analiseGenoma.dao;

import java.util.List;
import org.analiseGenoma.model.User;

public class UserDao extends DAO<User> {

    public UserDao() {
        super(User.class);
    }

//    @Override
//    public List<User> find() {
//        return manager.createQuery("Select u from User").getResultList();
//    }

    
    
}
