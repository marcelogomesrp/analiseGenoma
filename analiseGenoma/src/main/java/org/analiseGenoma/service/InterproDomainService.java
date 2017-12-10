package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.InterproDomainDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.InterproDomain;

@Named
public class InterproDomainService extends Service<InterproDomain>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public InterproDomainService() {
        super(InterproDomain.class);
    }
    
    private InterproDomainDao getDao(){
        return ((InterproDomainDao) dao);
    }
    
    public List<InterproDomain> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
