package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.ClinvarSignificance;

public class ClinvarSignificanceDao extends DAO<ClinvarSignificance> {

    public ClinvarSignificanceDao() {
        super(ClinvarSignificance.class);
    }

    public List<ClinvarSignificance> findByName(String name) {
        List<ClinvarSignificance> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM ClinvarSignificance i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
