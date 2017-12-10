package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.EnsemblDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Ensembl;

@Named
public class EnsemblService extends Service<Ensembl>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public EnsemblService() {
        super(Ensembl.class);
    }
    
    private EnsemblDao getDao(){
        return ((EnsemblDao) dao);
    }
    
    public List<Ensembl> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
