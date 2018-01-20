package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.PolyphenHvarDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.PolyphenHvar;

@Named
public class PolyphenHvarService extends Service<PolyphenHvar> {

    @Inject
    private GeneDao geneDao;
    @Inject
    private CromossomoDao cromossomoDao;

    public PolyphenHvarService() {
        super(PolyphenHvar.class);
    }

    private PolyphenHvarDao getDao() {
        return ((PolyphenHvarDao) dao);
    }

    public List<PolyphenHvar> findByName(String name) {
        return this.getDao().findByName(name);
    }

    @Transactional
    public PolyphenHvar findOrCreate(String name) {
        if(("-").equals(name))
            return null;
        List<PolyphenHvar> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        PolyphenHvar obj = new PolyphenHvar();
        obj.setName(name);
        this.persiste(obj);
        return obj;
    }

}
