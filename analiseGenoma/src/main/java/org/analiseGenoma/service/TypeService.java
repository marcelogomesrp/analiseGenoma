package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.analiseGenoma.dao.TypeDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Type;

@Named
public class TypeService extends Service<Type>{
    @Inject private GeneDao geneDao;
    @Inject private CromossomoDao cromossomoDao;
    
    public TypeService() {
        super(Type.class);
    }
    
    private TypeDao getDao(){
        return ((TypeDao) dao);
    }
    
    public List<Type> findByName(String name){
        return this.getDao().findByName(name);
    }
    
}
