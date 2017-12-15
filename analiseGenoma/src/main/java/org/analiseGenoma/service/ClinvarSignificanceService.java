package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.ClinvarSignificanceDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.ClinvarSignificance;

@Named
public class ClinvarSignificanceService extends Service<ClinvarSignificance>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public ClinvarSignificanceService() {
        super(ClinvarSignificance.class);
    }
    
    private ClinvarSignificanceDao getDao(){
        return ((ClinvarSignificanceDao) dao);
    }
    
    public List<ClinvarSignificance> findByName(String name){
        return this.getDao().findByName(name);
    }

    
    @Transactional
    public ClinvarSignificance findOrCreate(String name) {
        List<ClinvarSignificance> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        ClinvarSignificance obj = new ClinvarSignificance();
        obj.setName(name);
        this.adicionar(obj);
        return obj;
    }
    
    
}
