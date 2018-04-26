package org.analiseGenoma.dao;

import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.analiseGenoma.model.Filter;

public class FilterDao extends DAO<Filter> {

    public FilterDao() {
        super(Filter.class);
    }

    public List<Filter> findByName(String name) {
        List<Filter> list = null;
        try {
            Query query = manager.createQuery("SELECT i FROM Filter i WHERE i.name like :name");
            query.setParameter("name", name.toUpperCase());
            list = query.getResultList();
        } catch (NoResultException ex) {
            System.out.println("Erro:: " + ex.getMessage());
        }
        return list;
    }
}
