package org.analiseGenoma.service;

import org.analiseGenoma.dao.DbBioDao;
import org.analiseGenoma.model.DbBio;

public class DbBioService extends Service<DbBio> {

    public DbBioService() {
        super(DbBio.class);
    }

    private DbBioDao getDao() {
        return ((DbBioDao) dao);
    }
    
}

