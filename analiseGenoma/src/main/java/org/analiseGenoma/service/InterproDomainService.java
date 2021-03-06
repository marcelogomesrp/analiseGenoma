package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
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

    @Transactional
    public InterproDomain findOrCreate(String name) {
        if(("-").equals(name))
            return null;
        List<InterproDomain> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        InterproDomain obj = new InterproDomain();
        obj.setName(name);
        this.persiste(obj);
        return obj;
    }


}
