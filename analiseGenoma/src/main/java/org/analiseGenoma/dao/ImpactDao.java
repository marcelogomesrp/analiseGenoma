package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Impact;

public class ImpactDao extends DAO<Impact> {

    public ImpactDao() {
        super(Impact.class);
    }

    public List<Impact> findByName(String name) {
        List<Impact> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM Impact i WHERE i.name like :name");
            query.setParameter("name", name.toUpperCase());
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
