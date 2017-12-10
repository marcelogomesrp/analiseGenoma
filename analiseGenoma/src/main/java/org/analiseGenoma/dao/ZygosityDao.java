package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Zygosity;

public class ZygosityDao extends DAO<Zygosity> {

    public ZygosityDao() {
        super(Zygosity.class);
    }

    public List<Zygosity> findByName(String name) {
        List<Zygosity> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM Zygosity i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
