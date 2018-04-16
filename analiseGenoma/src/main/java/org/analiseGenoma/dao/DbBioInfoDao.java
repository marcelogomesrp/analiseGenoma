package org.analiseGenoma.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.DbBioInfo;
import org.analiseGenoma.model.DbBioInfoPK;
import org.analiseGenoma.model.Disease;
import org.analiseGenoma.model.Gene;

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

//    public  Set<Gene> findGeneByDisease(Disease disease) {
//        try {
//            Query query = manager.createQuery("SELECT i.genes FROM DbBioInfo i");
//            //WHERE g.nome like :nome");
//            //query.setParameter("nome", nome);
//            List<Gene> genes = query.getResultList();
//            return new HashSet<>(genes);
//        } catch (NoResultException ex) {
//            System.out.println("Erro:: " + ex.getMessage());
//            return null;
//        }        
//    }
    
}
