package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.TypeDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Filter;
import org.analiseGenoma.model.Type;

@Named
public class TypeService extends Service<Type> {

    @Inject
    private GeneDao geneDao;
    @Inject
    private CromossomoDao cromossomoDao;

    public TypeService() {
        super(Type.class);
    }

    private TypeDao getDao() {
        return ((TypeDao) dao);
    }

    public List<Type> findByName(String name) {
        return this.getDao().findByName(name);
    }

    @Transactional
    public Type findOrCreate(String name) {
        List<Type> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        Type obj = new Type();
        obj.setName(name);
        this.adicionar(obj);
        return obj;
    }
}
