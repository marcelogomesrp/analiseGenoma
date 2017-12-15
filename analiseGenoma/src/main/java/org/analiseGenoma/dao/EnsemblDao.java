package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Ensembl;

public class EnsemblDao extends DAO<Ensembl> {

    public EnsemblDao() {
        super(Ensembl.class);
    }

    public List<Ensembl> findByIdEnsembl(String idEnsembl) {
        List<Ensembl> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM Ensembl i WHERE i.idEnsembl like :idEnsembl");
            query.setParameter("idEnsembl", idEnsembl);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
