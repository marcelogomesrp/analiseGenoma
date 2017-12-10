package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.VariantType;

public class VariantTypeDao extends DAO<VariantType> {

    public VariantTypeDao() {
        super(VariantType.class);
    }

    public List<VariantType> findByName(String name) {
        List<VariantType> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM VariantType i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
