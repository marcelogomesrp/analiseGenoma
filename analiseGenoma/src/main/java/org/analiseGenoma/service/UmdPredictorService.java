package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.UmdPredictorDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.UmdPredictor;

@Named
public class UmdPredictorService extends Service<UmdPredictor>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public UmdPredictorService() {
        super(UmdPredictor.class);
    }
    
    private UmdPredictorDao getDao(){
        return ((UmdPredictorDao) dao);
    }
    
    public List<UmdPredictor> findByName(String name){
        return this.getDao().findByName(name);
    }

    @Transactional
    public UmdPredictor findOrCreate(String name) {
        if(("-").equals(name))
                return null;
        List<UmdPredictor> list = this.findByName(name);
        if(list.size() == 1)
            return list.get(0);
        UmdPredictor uPredictor = new UmdPredictor();
        uPredictor.setName(name);
        this.adicionar(uPredictor);
        return uPredictor;        
    }
    
}
