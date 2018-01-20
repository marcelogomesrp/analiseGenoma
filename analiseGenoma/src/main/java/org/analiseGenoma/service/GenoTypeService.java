package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.GenoTypeDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.GenoType;

@Named
public class GenoTypeService extends Service<GenoType> {

    @Inject
    private GeneDao geneDao;
    @Inject
    private CromossomoDao cromossomoDao;

    public GenoTypeService() {
        super(GenoType.class);
    }

    private GenoTypeDao getDao() {
        return ((GenoTypeDao) dao);
    }

    public List<GenoType> findByName(String name) {
        return this.getDao().findByName(name);
    }

    @Transactional
    public GenoType findOrCreate(String name) {
        if(("-").equals(name))
            return null;
        List<GenoType> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        GenoType obj = new GenoType();
        obj.setName(name);
        this.persiste(obj);
        return obj;
    }

}
