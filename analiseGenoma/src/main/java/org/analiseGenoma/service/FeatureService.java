package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.FeatureDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Feature;

@Named
public class FeatureService extends Service<Feature>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public FeatureService() {
        super(Feature.class);
    }
    
    private FeatureDao getDao(){
        return ((FeatureDao) dao);
    }
    
    public List<Feature> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
