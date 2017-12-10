package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.PolyphenHvarDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.PolyphenHvar;

@Named
public class PolyphenHvarService extends Service<PolyphenHvar>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public PolyphenHvarService() {
        super(PolyphenHvar.class);
    }
    
    private PolyphenHvarDao getDao(){
        return ((PolyphenHvarDao) dao);
    }
    
    public List<PolyphenHvar> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
