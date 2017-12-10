package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Sift;

public class SiftDao extends DAO<Sift> {

    public SiftDao() {
        super(Sift.class);
    }

    public List<Sift> findByName(String name) {
        List<Sift> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM Sift i WHERE i.name like :name");
            query.setParameter("name", name);
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
