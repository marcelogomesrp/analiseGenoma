package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.ClinvarAccessionDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.ClinvarAccession;

@Named
public class ClinvarAccessionService extends Service<ClinvarAccession>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public ClinvarAccessionService() {
        super(ClinvarAccession.class);
    }
    
    private ClinvarAccessionDao getDao(){
        return ((ClinvarAccessionDao) dao);
    }
    
    public List<ClinvarAccession> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
