package org.analiseGenoma.service;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.DbBioDao;
import org.analiseGenoma.dao.DbBioInfoDao;
import org.analiseGenoma.dao.DiseaseDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.DbBioInfo;
import org.analiseGenoma.model.Gene;
import org.analiseGenoma.service.util.CSVReader;
import org.analiseGenoma.service.util.Line;
import javax.transaction.Transactional;

@Named
public class DbBioService extends Service<DbBio> implements Serializable {

    @Inject
    private DbBioInfoDao infoDao;
    @Inject
    private DbBioDao dbBioDao;
    @Inject
    private DiseaseDao diseaseDao;
    @Inject
    private GeneDao geneDao;

//    @Inject
    //private DiseaseService diseaseService;
//    @Inject
//    private GeneService geneService;
    public DbBioService() {
        super(DbBio.class);
    }

    private DbBioDao getDao() {
        return ((DbBioDao) dao);
    }

    
    @Transactional
    public DbBioInfo persiste(DbBioInfo info) {
        try {
            //dbBioDao.merge(info.getDbBio());
            infoDao.persist(info);
        } catch (Exception ex) {
            System.out.println("-------> " + ex.getMessage());
        }
        return info;
    }

//    @Transactional
//    public void uploadInfo(byte[] contents, Long idBd) {
//        if (contents.length > 0) {
//            Runnable r = () -> {
//                this.importInfo(contents, idBd);
//            };
//            Thread t = new Thread(r);
//            t.start();
//        }
//    }
//    
    
    
    public void uploadInfo(byte[] contents, DbBio idBd) {
        if (contents.length > 0) {
                this.importInfo(contents, idBd);
        }
    }

    private void importInfo(byte[] contents, DbBio db) {
        CSVReader csv = new CSVReader(contents);
        //DbBio db = dbBioDao.findById(idBd);
        for (Line ln : csv.getFile()) {
            DbBioInfo info = new DbBioInfo();
            info.setDbBio(db);
            info.setDisease(diseaseDao.findByName(ln.getField(0)));
            DbBioInfo infoDb = infoDao.findById(info);
            if (infoDb == null) {
                //List<Gene> genes = new HashSet<>();
                Gene g = geneDao.findBySymbol(ln.getField(1));
                //genes.add(g);
                info.addGene(g);
                info.setInfoIdentifier(ln.getField(2));
                info.setUrl(ln.getField(3));
                this.persiste(info);
            }
        }
    }

//    public void update(DbBio dbBio) {
//        
//    }
    
}
//    
//    public DbBioInfo findById(DbBioInfo dbinfo) {
//        DbBioInfoPK id = new DbBioInfoPK();
//        //GeneDbBioPK id = new GeneDbBioPK();
//        id.setDbBio(dbinfo.getDbBio());
//        id.setDisease(dbinfo.getDisease());
//        return manager.find(DbBioInfo.class, id);
//    }




/*
 
        //List<Population> list = new ArrayList<>();
        for (Line ln : csv.getFile()) {
            if (ln.getSize() >= 1) {
                Population p = new Population();                
                p.setCode(ln.getField(0));
                if(ln.getSize() >= 2)
                    p.setDescription(ln.getField(1));
                if(ln.getSize() >= 3)
                    p.setSuperPopulation(this.findOrCreateByCode(ln.getField(2)));
                //list.add(p);
                this.persiste(p);
            }
        }
 */
