package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.VariantStatusDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.VariantStatus;

@Named
public class VariantStatusService extends Service<VariantStatus>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public VariantStatusService() {
        super(VariantStatus.class);
    }
    
    private VariantStatusDao getDao(){
        return ((VariantStatusDao) dao);
    }
    
    public List<VariantStatus> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
