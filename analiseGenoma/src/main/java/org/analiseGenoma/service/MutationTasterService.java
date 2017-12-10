package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.MutationTasterDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.MutationTaster;

@Named
public class MutationTasterService extends Service<MutationTaster>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public MutationTasterService() {
        super(MutationTaster.class);
    }
    
    private MutationTasterDao getDao(){
        return ((MutationTasterDao) dao);
    }
    
    public List<MutationTaster> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
