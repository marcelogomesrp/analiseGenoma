package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.ClinvarAlleleOriginDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.ClinvarAlleleOrigin;

@Named
public class ClinvarAlleleOriginService extends Service<ClinvarAlleleOrigin>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public ClinvarAlleleOriginService() {
        super(ClinvarAlleleOrigin.class);
    }
    
    private ClinvarAlleleOriginDao getDao(){
        return ((ClinvarAlleleOriginDao) dao);
    }
    
    public List<ClinvarAlleleOrigin> findByName(String name){
        return this.getDao().findByName(name);
    }

    @Transactional
    public ClinvarAlleleOrigin findOrCreate(String name) {
        List<ClinvarAlleleOrigin> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        ClinvarAlleleOrigin obj = new ClinvarAlleleOrigin();
        obj.setName(name);
        this.adicionar(obj);
        return obj;
    }
}
