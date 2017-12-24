package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.SiftDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Sift;

@Named
public class SiftService extends Service<Sift>{


    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public SiftService() {
        super(Sift.class);
    }
    
    private SiftDao getDao(){
        return ((SiftDao) dao);
    }
    
    public List<Sift> findByName(String name){
        return this.getDao().findByName(name);
    }
    
    @Transactional
    public Sift findOrCreate(String name) {
        if(("-").equals(name))
            return null;
        List<Sift> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        Sift obj = new Sift();
        obj.setName(name);
        this.adicionar(obj);
        return obj;
    }
    
}
