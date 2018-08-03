package org.analiseGenoma.dao;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.DbBioInfo;
import org.analiseGenoma.model.DbBioInfoPK;
import org.analiseGenoma.model.Disease;

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
    public DbBioInfo findByIDbIdDisease(Long idDb, Long idDisease) {
        Query q = manager.createQuery("SELECT i FROM DbBioInfo i WHERE i.dbBio.id = :idBd and i.disease.id = :idDisease");
        q.setParameter("idBd", idDb);
        q.setParameter("idDisease", idDisease);
        List<DbBioInfo> l = q.getResultList();
        return this.getFirstOrNull(l);
    }

    public DbBioInfo findByIDbIdDiseaseIdGene(Long idDb, Long idDisease, Long geneId) {
//        Query q = manager.createQuery("SELECT i FROM DbBioInfo i WHERE i.dbBio.id = :idBd and i.disease.id = :idDisease");
        //Query q = manager.createQuery("SELECT i FROM DbBioInfo i WHERE i.dbBio.id = :idBd and i.disease.id = :idDisease and i.genes.id in (:geneId)");
        Query q = manager.createQuery("SELECT i FROM DbBioInfo i INNER JOIN i.genes g WHERE i.dbBio.id = :idBd and i.disease.id = :idDisease and g.id = :geneId");

        q.setParameter("idBd", idDb);
        q.setParameter("idDisease", idDisease);
        q.setParameter("geneId", geneId);
        List<DbBioInfo> l = q.setMaxResults(2).getResultList();
//        List<DbBioInfo> l = q.getResultList();
        if (l.isEmpty()) {
            return null;
        } else {
            return l.get(0);
        }
        //return this.getFirstOrNull(l);
    }

    public List<DbBioInfo> findComplete() {
        try {
            Query query = manager.createQuery("SELECT DISTINCT i FROM DbBioInfo i JOIN FETCH i.genes");
            List<DbBioInfo> list = query.getResultList();
            return list;
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
    }

    //retornar uma doença
    public DbBioInfo findByDsease(Disease patologia) {
        try {
            Query query = manager.createQuery("SELECT DISTINCT i FROM DbBioInfo i JOIN FETCH i.genes WHERE i.dbBio.id = :id AND i.disease = :disease");
            query.setParameter("id", 1L);
            query.setParameter("disease", patologia);
            List<DbBioInfo> list = query.getResultList();
            if(list.size() == 1){
                return list.get(0);
            }            
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
            return null;
        }
        return new DbBioInfo();
    }

}
