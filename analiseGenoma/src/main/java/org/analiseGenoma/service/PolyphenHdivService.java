package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.PolyphenHdivDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.PolyphenHdiv;

@Named
public class PolyphenHdivService extends Service<PolyphenHdiv> {

    @Inject
    private GeneDao geneDao;
    @Inject
    private CromossomoDao cromossomoDao;

    public PolyphenHdivService() {
        super(PolyphenHdiv.class);
    }

    private PolyphenHdivDao getDao() {
        return ((PolyphenHdivDao) dao);
    }

    public List<PolyphenHdiv> findByName(String name) {
        return this.getDao().findByName(name);
    }

    @Transactional
    public PolyphenHdiv findOrCreate(String name) {
        if(("-").equals(name))
            return null;
        List<PolyphenHdiv> list = this.findByName(name);
        if (list.size() == 1) {
            return list.get(0);
        }
        PolyphenHdiv obj = new PolyphenHdiv();
        obj.setName(name);
        this.persiste(obj);
        return obj;
    }

}
