package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.PolyphenHvar;

public class PolyphenHvarDao extends DAO<PolyphenHvar> {

    public PolyphenHvarDao() {
        super(PolyphenHvar.class);
    }

    public List<PolyphenHvar> findByName(String name) {
        List<PolyphenHvar> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM PolyphenHvar i WHERE i.name like :name");
            query.setParameter("name", name.toUpperCase());
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
