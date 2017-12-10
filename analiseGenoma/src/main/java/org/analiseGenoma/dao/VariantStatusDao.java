package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.VariantStatus;

public class VariantStatusDao extends DAO<VariantStatus> {

    public VariantStatusDao() {
        super(VariantStatus.class);
    }

    public List<VariantStatus> findByName(String name) {
        List<VariantStatus> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM VariantStatus i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
