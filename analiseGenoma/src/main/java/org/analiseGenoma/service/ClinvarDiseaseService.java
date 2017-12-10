package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.ClinvarDiseaseDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.ClinvarDisease;

@Named
public class ClinvarDiseaseService extends Service<ClinvarDisease>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public ClinvarDiseaseService() {
        super(ClinvarDisease.class);
    }
    
    private ClinvarDiseaseDao getDao(){
        return ((ClinvarDiseaseDao) dao);
    }
    
    public List<ClinvarDisease> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
