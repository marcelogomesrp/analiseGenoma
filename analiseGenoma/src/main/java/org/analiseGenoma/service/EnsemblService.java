package org.analiseGenoma.service;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import org.analiseGenoma.dao.EnsemblDao;
import org.analiseGenoma.dao.CromossomoDao;
import org.analiseGenoma.dao.GeneDao;
import org.analiseGenoma.model.Ensembl;

@Named
public class EnsemblService extends Service<Ensembl> {

    @Inject
    private GeneDao geneDao;
    @Inject
    private CromossomoDao cromossomoDao;

    public EnsemblService() {
        super(Ensembl.class);
    }

    private EnsemblDao getDao() {
        return ((EnsemblDao) dao);
    }

    public List<Ensembl> findByIdEnsembl(String idEnsembl) {
        return this.getDao().findByIdEnsembl(idEnsembl);
    }

    @Transactional
    public Ensembl findOrCreate(String idEnsembl) {
        if(("-").equals(idEnsembl))
            return null;
        List<Ensembl> list = this.findByIdEnsembl(idEnsembl);
        if (list.size() == 1) {
            return list.get(0);
        }
        Ensembl obj = new Ensembl();
        obj.setIdEnsembl(idEnsembl);
        this.adicionar(obj);
        return obj;
    }

}
