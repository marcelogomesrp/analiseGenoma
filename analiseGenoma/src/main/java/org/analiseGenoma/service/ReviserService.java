package org.analiseGenoma.service;

import java.io.Serializable;
import javax.inject.Named;
import org.analiseGenoma.dao.UserDao;
import org.analiseGenoma.model.User;

@Named
public class ReviserService extends Service<User> implements Serializable {

    public ReviserService() {
        super(User.class);
    }

    private UserDao getDao() {
        return ((UserDao) dao);
    }
    
    public String getOpiniao(String idRevisor, Long idVariant){
        return "ok";
    }

}
