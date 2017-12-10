package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.VarianteRevisadaDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.VarianteRevisada;

@Named
public class VarianteRevisadaService extends Service<VarianteRevisada>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public VarianteRevisadaService() {
        super(VarianteRevisada.class);
    }
    
    private VarianteRevisadaDao getDao(){
        return ((VarianteRevisadaDao) dao);
    }
    
    public List<VarianteRevisada> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
