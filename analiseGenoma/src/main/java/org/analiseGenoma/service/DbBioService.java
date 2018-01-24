package org.analiseGenoma.service;

import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.DbBioDao;
import org.analiseGenoma.dao.DbBioInfoDao;
import org.analiseGenoma.model.DbBio;
import org.analiseGenoma.model.DbBioInfo;
import org.analiseGenoma.service.util.CSVReader;
import org.analiseGenoma.service.util.Line;


@Named
public class DbBioService extends Service<DbBio> implements Serializable{

    @Inject
    private DbBioInfoDao infoDao;
    
    public DbBioService() {
        super(DbBio.class);
    }

    private DbBioDao getDao() {
        return ((DbBioDao) dao);
    }
    
    public DbBioInfo persiste(DbBioInfo info){
        infoDao.persist(info);
        return info;
    }


    @Transactional
    public void uploadInfo(byte[] contents, Long idBd) {
        if (contents.length > 0) {
            Runnable r = () -> {
                this.importInfo(contents, idBd);
            };
            Thread t = new Thread(r);
            t.start();
        }
    }

    private void importInfo(byte[] contents, Long idBd) {
        CSVReader csv = new CSVReader(contents);       
        for (Line ln : csv.getFile()) {
            DbBioInfo info = new DbBioInfo();
            //info.set
            this.persiste(info);
        }
    }
    
}
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

