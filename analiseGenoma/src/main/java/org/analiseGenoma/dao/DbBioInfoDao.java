package org.analiseGenoma.dao;

import org.analiseGenoma.model.DbBioInfo;
import org.analiseGenoma.model.DbBioInfoPK;

public class DbBioInfoDao extends DAO<DbBioInfo> {

    public DbBioInfoDao() {
        super(DbBioInfo.class);
    }

    public DbBioInfo findById(DbBioInfo dbinfo) {
        DbBioInfoPK id = new DbBioInfoPK();
        //GeneDbBioPK id = new GeneDbBioPK();
        id.setDbBio(dbinfo.getDbBio());
        id.setDisease(dbinfo.getDisease());
        return manager.find(DbBioInfo.class, id);
    }
    
}
