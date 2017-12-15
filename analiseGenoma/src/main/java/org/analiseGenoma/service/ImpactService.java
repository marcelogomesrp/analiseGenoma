package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.ImpactDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Impact;

@Named
public class ImpactService extends Service<Impact>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public ImpactService() {
        super(Impact.class);
    }
    
    private ImpactDao getDao(){
        return ((ImpactDao) dao);
    }
    
    public List<Impact> findByName(String name){
        return this.getDao().findByName(name);
    }

    @Transactional
    public Impact findOrCreate(String name) {
        List<Impact> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        Impact obj = new Impact();
        obj.setName(name);
        this.adicionar(obj);
        return obj;
    }
    
}
