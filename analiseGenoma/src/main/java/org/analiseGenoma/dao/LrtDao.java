package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Lrt;

public class LrtDao extends DAO<Lrt> {

    public LrtDao() {
        super(Lrt.class);
    }

    public List<Lrt> findByName(String name) {
        List<Lrt> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM Lrt i WHERE i.name like :name");
            query.setParameter("name", name.toUpperCase());
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
