package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.VariantTypeDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.VariantType;

@Named
public class VariantTypeService extends Service<VariantType>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public VariantTypeService() {
        super(VariantType.class);
    }
    
    private VariantTypeDao getDao(){
        return ((VariantTypeDao) dao);
    }
    
    public List<VariantType> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
