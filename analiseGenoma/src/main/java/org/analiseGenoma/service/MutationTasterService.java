package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
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

    @Transactional
    public MutationTaster findOrCreate(String name) {
        
        if(("-").equals(name))
            return null;
        List<MutationTaster> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        MutationTaster obj = new MutationTaster();
        obj.setName(name);
        this.persiste(obj);
        return obj;
    }
    
}
