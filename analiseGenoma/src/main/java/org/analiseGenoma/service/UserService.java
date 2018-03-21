package org.analiseGenoma.service;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.UserDao;
import org.analiseGenoma.model.User;

@Named
public class UserService  extends Service<User> implements Serializable {

    @Inject
    private UserDao userDao;
    @Inject
    private FacesContext context;
    
    public UserService() {
        super(User.class);
    }

    private UserDao getDao() {
        return ((UserDao) dao);
    }

    @Transactional
    public void persiste(User user, String confirmPassword) throws Exception {
        if(confirmPassword.equals(user.getPassword())){
            user.setRowsInTable(10);
            this.persiste(user);
        }else{
            throw new Exception("Password isn't valid");
        }
    }

    public User findManagerByEmailPassword(User user) throws Exception {
        User userFind = new User();
        userFind.setEmail(user.getEmail());
        userFind.setPassword(user.getPassword());
        userFind.setManager(true);
        return this.getFirstOrNull(this.findByExample(userFind));
    }
    
}
