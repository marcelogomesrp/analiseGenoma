package org.analiseGenoma.service;

import java.io.Serializable;
import javax.inject.Named;
import org.analiseGenoma.dao.DbBioDao;
import org.analiseGenoma.model.DbBio;


@Named
public class DbBioService extends Service<DbBio> implements Serializable{

    public DbBioService() {
        super(DbBio.class);
    }

    private DbBioDao getDao() {
        return ((DbBioDao) dao);
    }
    
}

