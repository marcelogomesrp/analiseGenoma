package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.ClinvarAlleleTypeDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.ClinvarAlleleType;

@Named
public class ClinvarAlleleTypeService extends Service<ClinvarAlleleType>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public ClinvarAlleleTypeService() {
        super(ClinvarAlleleType.class);
    }
    
    private ClinvarAlleleTypeDao getDao(){
        return ((ClinvarAlleleTypeDao) dao);
    }
    
    public List<ClinvarAlleleType> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
