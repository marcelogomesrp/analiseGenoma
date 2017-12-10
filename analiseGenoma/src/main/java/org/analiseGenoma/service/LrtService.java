package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.LrtDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Lrt;

@Named
public class LrtService extends Service<Lrt>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public LrtService() {
        super(Lrt.class);
    }
    
    private LrtDao getDao(){
        return ((LrtDao) dao);
    }
    
    public List<Lrt> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
