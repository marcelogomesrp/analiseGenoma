package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import org.analiseGenoma.dao.AgeDao;
import org.analiseGenoma.model.Age;

@Named
public class AgeService extends Service<Age> implements Serializable{

    public AgeService() {
        super(Age.class);
    }

    private AgeDao getDao() {
        return ((AgeDao) dao);
    }
    
    public List<Age> findByAge(int age){
        return getDao().findByAge(age);
    }

}