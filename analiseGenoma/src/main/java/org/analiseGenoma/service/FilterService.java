package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.FilterDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Filter;

@Named
public class FilterService extends Service<Filter> {

    @Inject
    private GeneDao geneDao;
    @Inject
    private CromossomoDao cromossomoDao;

    public FilterService() {
        super(Filter.class);
    }

    private FilterDao getDao() {
        return ((FilterDao) dao);
    }

    public List<Filter> findByName(String name) {
        return this.getDao().findByName(name);
    }

    @Transactional
    public Filter findOrCreate(String name) {
        List<Filter> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        Filter obj = new Filter();
        obj.setName(name);
        this.persiste(obj);
        return obj;
    }

}
