package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.ZygosityDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.UmdPredictor;
import org.analiseGenoma.model.Zygosity;

@Named
public class ZygosityService extends Service<Zygosity>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public ZygosityService() {
        super(Zygosity.class);
    }
    
    private ZygosityDao getDao(){
        return ((ZygosityDao) dao);
    }
    
    public List<Zygosity> findByName(String name){
        return this.getDao().findByName(name);
    }

    @Transactional
    public Zygosity findOrCreate(String name) {
        List<Zygosity> list = this.findByName(name.toUpperCase());
        if(list.size() == 1)
            return list.get(0);
        Zygosity obj = new Zygosity();
        obj.setName(name);
        this.persiste(obj);
        return obj;    
    }
    
}
